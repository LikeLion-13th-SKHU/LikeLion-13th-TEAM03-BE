package team3.sambakja.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.client.Client;
import team3.sambakja.dto.DongRequest;
import team3.sambakja.dto.DongResponse;
import team3.sambakja.dto.RegionRequest;
import team3.sambakja.dto.RegionResponse;

import java.util.List;

@RestController
public class ReportController {

    private final Client client;

    public ReportController(Client client) {
        this.client = client;
    }

    @PostMapping("/report")
    public List<RegionResponse> getReport(@RequestBody RegionRequest regionRequest){
        return client.getReport(regionRequest);
    }

    @PostMapping("recommend")
    public DongResponse getRecommend(@RequestBody DongRequest dongRequest) {
        return client.getRecommend(dongRequest);
    }
}
