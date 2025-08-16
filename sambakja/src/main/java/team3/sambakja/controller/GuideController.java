package team3.sambakja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.service.GuideService;

@RestController
@RequestMapping("/api/guide")
@RequiredArgsConstructor
public class GuideController {

    private final GuideService guideService;

    @Operation(
            summary = "창업 가이드 조회",
            description = "소상공인을 위한 창업 가이드 정보를 JSON 형식으로 반환합니다.!!"
    )
    @ApiResponse(responseCode = "200", description = "가이드 데이터 반환")
    @GetMapping("/startup")
    public ResponseEntity<String> getStartup() {
        String response = guideService.getStartup();

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response);
    }

}
