package kr.hhplus.be.server.common.error;

public class CustomExceptionHandler extends RuntimeException{

    private final ErrorCode errorCode;

    public CustomExceptionHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CustomExceptionHandler(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public int getHttpStatus() {
        return errorCode.getHttpStatus();
    }

    public String getCustomCode() {
        return errorCode.getCustomCode();
    }

    public String getErrorMessage() {
        return errorCode.getMessage();
    }

}
