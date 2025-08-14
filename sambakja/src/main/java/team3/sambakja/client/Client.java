package team3.sambakja.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team3.sambakja.dto.*;
import java.util.Optional;

@Component
public class Client {

    private final RestTemplate restTemplate;

    @Value("${ai.service.report-url}")
    private String reportUrl;

    @Value("${ai.service.recommend-url}")
    private String recommendUrl;

    public Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public RegionListResponse getReport(RegionRequest regionRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RegionRequest> request = new HttpEntity<>(regionRequest, headers);

        return Optional.ofNullable(
                restTemplate.postForObject(reportUrl, request, RegionListResponse.class)
        ).orElse(new RegionListResponse(java.util.List.of()));
    }


    public DongResponse getRecommend(DongRequest dongRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DongRequest> request = new HttpEntity<>(dongRequest, headers);

        DongResponse response = restTemplate.postForObject(recommendUrl, request, DongResponse.class);

        return Optional.ofNullable(response)
            .orElse(new DongResponse("", new DongResponse.Report(
                new DongResponse.Population("", ""),
                new DongResponse.Business("", ""),
                ""
            )));
    }

}
