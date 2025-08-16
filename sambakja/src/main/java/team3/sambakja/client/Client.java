package team3.sambakja.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team3.sambakja.dto.request.BizRequest;
import team3.sambakja.dto.request.DongRequest;
import team3.sambakja.dto.request.RegionRequest;
import team3.sambakja.dto.response.BizResponse;
import team3.sambakja.dto.response.DongResponse;
import team3.sambakja.dto.response.RegionListResponse;
import java.util.List;
import java.util.Optional;

@Component
public class Client {

    private final RestTemplate restTemplate;

    @Value("${api.service.region-dong-url}")
    private String regionDongUrl;

    @Value("${api.service.region-report-url}")
    private String regionReportUrl;

    @Value("${api.service.biz-recommend-url}")
    private String bizRecommendUrl;

    public Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RegionListResponse fetchDongListByRegion(RegionRequest regionRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RegionRequest> request = new HttpEntity<>(regionRequest, headers);

        return Optional.ofNullable(
                restTemplate.postForObject(regionDongUrl, request, RegionListResponse.class)
        ).orElse(new RegionListResponse(java.util.List.of()));
    }

    public DongResponse fetchRegionReportByDong(DongRequest dongRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DongRequest> request = new HttpEntity<>(dongRequest, headers);

        DongResponse response = restTemplate.postForObject(regionReportUrl, request, DongResponse.class);

        return Optional.ofNullable(response)
            .orElse(new DongResponse("", new DongResponse.Report(
                new DongResponse.Population("", ""),
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
                requestDto.sex(),
                "추천 정보 조회에 실패하였습니다.",
                List.of()
        ));
    }

}
