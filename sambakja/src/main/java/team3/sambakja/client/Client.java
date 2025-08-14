package team3.sambakja.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import team3.sambakja.dto.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class Client {

    private final RestTemplate restTemplate;
    private final String URL;

    public Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.URL = "${url~~}";
    }


    public List<RegionResponse> getReport(RegionRequest regionRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RegionRequest> request = new HttpEntity<>(regionRequest, headers);

        RegionResponse[] responses = restTemplate.postForObject(URL, request, RegionResponse[].class);

        return Optional.ofNullable(responses)
            .map(Arrays::asList)
            .orElse(List.of());
    }

    public DongResponse getRecommend(DongRequest dongRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DongRequest> request = new HttpEntity<>(dongRequest, headers);

        DongResponse response = restTemplate.postForObject(URL, request, DongResponse.class);

        return Optional.ofNullable(response)
            .orElse(new DongResponse("", new DongResponse.Report(
                new DongResponse.Population("", ""),
                new DongResponse.Business("", ""),
                ""
            )));
    }

}
