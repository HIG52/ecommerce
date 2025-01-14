package kr.hhplus.be.server.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 커스텀 예외처리
    @ExceptionHandler(CustomExceptionHandler.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomExceptionHandler ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                ex.getHttpStatus(),
                ex.getCustomCode(),
                ex.getErrorMessage()
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(errorResponse);
    }

    // 일반적인 예외 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "G001", "알 수 없는 오류가 발생했습니다.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // 메서드에 잘못된 파라미터 전달시 (예: IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "G002", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // @Valid로 검증 실패 시 (예: MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "G003", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // 지원하지 않는 HTTP 메서드로 요청하였을때
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse errorResponse =
                new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), "G004", "지원하지 않는 HTTP 메서드입니다.");
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }



}
