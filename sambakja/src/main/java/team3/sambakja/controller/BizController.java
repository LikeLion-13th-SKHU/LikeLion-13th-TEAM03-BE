package team3.sambakja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.client.Client;
import team3.sambakja.dto.request.BizRequest;
import team3.sambakja.dto.response.BizResponse;

@RestController
@RequestMapping("/api/biz")
@RequiredArgsConstructor
public class BizController {

    private final Client client;

    @Operation(
            summary = "업종 추천 요청",
            description = "선택된 업종에 대한 특징과 추천 지역 리스트를 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공적으로 업종 분석 결과 반환", content = @Content(schema = @Schema(implementation = BizResponse.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content)
    })
    @PostMapping("/recommendation")
    public BizResponse getBizRecommendation(@RequestBody BizRequest request) {
        return client.fetchBizRecommendation(request);
    }
}
