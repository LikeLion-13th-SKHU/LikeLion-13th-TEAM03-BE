package team3.sambakja.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team3.sambakja.dto.request.InquiryRequest;
import team3.sambakja.service.InquiryService;

@RestController
@RequestMapping("/api/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    @Operation(
            summary = "문의 이메일 전송",
            description = "사용자의 이름, 연락처, 이메일, 문의 내용을 입력 받아 담당자 이메일로 전송합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문의 전송 성공"),
            @ApiResponse(responseCode = "500", description = "이메일 전송 실패")
    })
    @PostMapping
    public void submitInquiry(@RequestBody InquiryRequest request) {
        inquiryService.sendInquiryEmail(request);
    }

}
