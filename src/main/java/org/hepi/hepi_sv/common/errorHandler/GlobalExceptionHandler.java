package org.hepi.hepi_sv.common.errorHandler;

import org.hepi.hepi_sv.auth.exception.TokenException;
import org.hepi.hepi_sv.user.Exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // 전역 예외 처리 핸들러

    // 인증 오류
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(CustomException ex) {
        log.error("Authentication error", ex);
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse response = new ErrorResponse(errorCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 토큰 오류
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(CustomException ex) {
        log.error("Token error", ex);
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse response = new ErrorResponse(errorCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 유저 오류
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(CustomException ex) {
        log.error("User error", ex);
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse response = new ErrorResponse(errorCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // 기본 예외 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        log.error("Internal Server error", ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}

