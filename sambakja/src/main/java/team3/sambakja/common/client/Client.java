package team3.sambakja.common.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import team3.sambakja.common.apiPayload.exception.GeneralException;
import team3.sambakja.common.apiPayload.status.ErrorStatus;
import team3.sambakja.dto.request.BizRequest;
import team3.sambakja.dto.request.DongRequest;
import team3.sambakja.dto.response.BizResponse;
import team3.sambakja.dto.response.DongResponse;
import java.net.URI;
import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class Client {

    private final RestTemplate restTemplate;

    @Value("${api.service.region-report-url}")
    private String regionReportUrl;

    @Value("${api.service.biz-recommend-url}")
    private String bizRecommendUrl;

    private final Logger log = org.slf4j.LoggerFactory.getLogger(Client.class);

    public DongResponse fetchRegionReportByDong(DongRequest dongRequest) {
        return postAndExtract(regionReportUrl, dongRequest, DongResponse.class);
    }

    public BizResponse fetchBizRecommendation(BizRequest requestDto) {
        return postAndExtract(bizRecommendUrl, requestDto, BizResponse.class);
    }

    private HttpHeaders jsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private <T> HttpEntity<T> jsonEntity(T body) {
        return new HttpEntity<>(body, jsonHeaders());
    }

    private URI safeUri(String url) {
        try {
            return new URI(url);
        } catch (URISyntaxException e) {
            throw new GeneralException(ErrorStatus.AI_SERVER_INVALID_URI);
        }
    }

    private <T, R> ResponseEntity<R> safePost(String url, T body, Class<R> responseType) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(">>> POST 요청 URL: " + url);
            System.out.println(">>> 전송 Body(JSON): " + mapper.writeValueAsString(body));
            System.out.println(">>> 요청 헤더: " + jsonHeaders());

            return restTemplate.exchange(
                safeUri(url),
                HttpMethod.POST,
                jsonEntity(body),
                responseType
            );

        } catch (HttpStatusCodeException e) {
            System.err.println(" AI 서버 응답 오류");
            System.err.println("상태 코드: " + e.getStatusCode());
            System.err.println("응답 바디: " + e.getResponseBodyAsString());
            throw new GeneralException(ErrorStatus.AI_SERVER_COMMUNICATION_ERROR);

        } catch (IllegalArgumentException e) {
            System.err.println("URI가 잘못되었습니다: " + url);
            throw new GeneralException(ErrorStatus.AI_SERVER_INVALID_URI);

        } catch (org.springframework.http.converter.HttpMessageConversionException e) {
            System.err.println("JSON 직렬화/역직렬화 에러: " + e.getMessage());
            throw new GeneralException(ErrorStatus.AI_SERVER_IO_ERROR);

        } catch (Exception e) {
            System.err.println("기타 예외: " + e.getMessage());
            throw new GeneralException(ErrorStatus.AI_SERVER_COMMUNICATION_ERROR);
        }
    }

    private <R> void validateResponse(ResponseEntity<R> resp) {
        if (!resp.getStatusCode().is2xxSuccessful() || resp.getBody() == null) {
            throw new GeneralException(ErrorStatus.AI_SERVER_COMMUNICATION_ERROR);
        }
    }

    private <T, R> R postAndExtract(String url, T body, Class<R> responseType) {
        ResponseEntity<R> resp = safePost(url, body, responseType);
        validateResponse(resp);
        return resp.getBody();
    }
}
