package team3.sambakja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.common.client.Client;
import team3.sambakja.dto.request.BizRequest;
import team3.sambakja.dto.response.BizResponse;

@RestController
@RequestMapping("/api/biz")
@RequiredArgsConstructor
@Tag(name = "업종 추천 리포트 API")
public class BizController {

    private final Client client;

    @Operation(
            summary = "업종 추천 리포트 조회",
            description = "선택된 업종에 대한 특징과 추천 지역 리스트를 반환합니다."
    )
    @PostMapping("/recommendation")
    public BizResponse getBizRecommendation(@RequestBody BizRequest request) {
        return client.fetchBizRecommendation(request);
    }
}
