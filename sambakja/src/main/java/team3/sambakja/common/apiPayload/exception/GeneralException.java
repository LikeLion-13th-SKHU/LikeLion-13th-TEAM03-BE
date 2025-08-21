package team3.sambakja.common.apiPayload.exception;

import lombok.Getter;
import team3.sambakja.common.apiPayload.BaseErrorCode;
import team3.sambakja.common.apiPayload.ErrorReasonDto;

@Getter
public class GeneralException extends RuntimeException {
    private final BaseErrorCode code;

    public GeneralException(BaseErrorCode code) {
        super(code.getReason().getMessage());
        this.code = code;
    }

    public ErrorReasonDto getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDto getErrorReasonHttpStatus() {
        return this.code.getReasonHttpStatus();
    }
}
