package team3.sambakja.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team3.sambakja.dto.request.BizRequest;
import team3.sambakja.dto.request.DongRequest;
import team3.sambakja.dto.response.BizResponse;
import team3.sambakja.dto.response.DongResponse;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Client {

    private final RestTemplate restTemplate;

    @Value("${api.service.region-report-url}")
    private String regionReportUrl;

    @Value("${api.service.biz-recommend-url}")
    private String bizRecommendUrl;

    public DongResponse fetchRegionReportByDong(DongRequest dongRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DongRequest> request = new HttpEntity<>(dongRequest, headers);
        return Optional.ofNullable(
                restTemplate.postForObject(regionReportUrl, request, DongResponse.class)
        ).orElse(new DongResponse("", new DongResponse.Report(
                new DongResponse.Population("", "", "", ""),
                new DongResponse.Business("", ""),
                ""
        )));
    }

    public BizResponse fetchBizRecommendation(BizRequest requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BizRequest> request = new HttpEntity<>(requestDto, headers);
        return Optional.ofNullable(
                restTemplate.postForObject(bizRecommendUrl, request, BizResponse.class)
        ).orElse(new BizResponse(
                requestDto.type_small(),
                "추천 정보 조회에 실패하였습니다.",
                List.of()
        ));
    }
}
