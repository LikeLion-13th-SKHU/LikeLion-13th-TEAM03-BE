package team3.sambakja.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.client.Client;
import team3.sambakja.dto.DongRequest;
import team3.sambakja.dto.DongResponse;
import team3.sambakja.dto.RegionRequest;
import team3.sambakja.dto.RegionListResponse;

@RestController("/api/region")
public class ReportController {

    private final Client client;

    public ReportController(Client client) {
        this.client = client;
    }

    @PostMapping("/dongs")
    public RegionListResponse getDongList(@RequestBody RegionRequest regionRequest){
        return client.fetchDongListByRegion(regionRequest);
    }

    @PostMapping("/report")
    public DongResponse getRegionReport(@RequestBody DongRequest dongRequest) {
        return client.fetchRegionReportByDong(dongRequest);
    }
}
