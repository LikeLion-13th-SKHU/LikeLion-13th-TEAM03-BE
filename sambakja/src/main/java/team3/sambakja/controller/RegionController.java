package team3.sambakja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.common.client.Client;
import team3.sambakja.dto.request.DongRequest;
import team3.sambakja.dto.response.DongResponse;

@RestController
@RequestMapping("/api/region")
@Tag(name = "지역 리포트 API")
public class RegionController {

    private final Client client;

    public RegionController(Client client) {
        this.client = client;
    }

    @Operation(
            summary = "상권 분석 리포트 조회",
            description = "선택된 행정동에 대한 상권 리포트를 반환합니다."
    )
    @PostMapping("/report")
    public DongResponse getRegionReport(@RequestBody DongRequest dongRequest) {
        return client.fetchRegionReportByDong(dongRequest);
    }
}
