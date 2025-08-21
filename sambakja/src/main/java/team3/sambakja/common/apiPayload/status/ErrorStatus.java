package team3.sambakja.common.apiPayload.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import team3.sambakja.common.apiPayload.BaseErrorCode;
import team3.sambakja.common.apiPayload.ErrorReasonDto;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),

    // AI 서버 관련 에러
    AI_SERVER_INVALID_URI(HttpStatus.INTERNAL_SERVER_ERROR, "AI5001", "AI 서버 URI가 잘못되었습니다."),
    AI_SERVER_IO_ERROR(HttpStatus.BAD_GATEWAY, "AI5002", "AI 서버 응답 포맷이 올바르지 않습니다."),
    AI_SERVER_COMMUNICATION_ERROR(HttpStatus.SERVICE_UNAVAILABLE, "AI5004", "AI 서버와의 통신에 실패했습니다."),

    // GuideService 관련 에러
    GUIDE_STARTUP_API_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "GUIDE5001", "창업 지원 API 조회에 실패했습니다."),
    GUIDE_STARTUP_JSON_PARSE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "GUIDE5002", "창업 지원 API 응답 파싱에 실패했습니다."),

    // Inquiry 관련 에러
    INQUIRY_MAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "INQUIRY5001", "문의 메일 발송에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDto getReason() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
