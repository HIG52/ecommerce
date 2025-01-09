package kr.hhplus.be.server.common.error;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "에러 응답 DTO")
public class ErrorResponse {

    @Schema(description = "에러 코드", example = "400")
    private int errorCode;

    @Schema(description = "에러 메시지", example = "잘못된 요청입니다.")
    private String errorMessage;

    public ErrorResponse(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
