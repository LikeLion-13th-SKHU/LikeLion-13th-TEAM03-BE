package team3.sambakja.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.client.Client;
import team3.sambakja.dto.request.DongRequest;
import team3.sambakja.dto.response.DongResponse;
import team3.sambakja.dto.request.RegionRequest;
import team3.sambakja.dto.response.RegionListResponse;

@RestController("/api/region")
public class RegionController {

    private final Client client;

    public RegionController(Client client) {
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
