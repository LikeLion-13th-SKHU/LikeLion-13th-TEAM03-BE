package team3.sambakja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.client.Client;
import team3.sambakja.dto.request.DongRequest;
import team3.sambakja.dto.response.DongResponse;

@RestController
@RequestMapping("/api/region")
public class RegionController {

    private final Client client;

    public RegionController(Client client) {
        this.client = client;
    }

    @Operation(
            summary = "특정 행정동의 리포트 조회",
            description = "선택된 행정동(dong)에 대한 상권 리포트를 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "리포트 반환", content = @Content(schema = @Schema(implementation = DongResponse.class))),
            @ApiResponse(responseCode = "400", description = "요청 오류", content = @Content)
    })
    @PostMapping("/report")
    public DongResponse getRegionReport(@RequestBody DongRequest dongRequest) {
        return client.fetchRegionReportByDong(dongRequest);
    }
}
