package org.bodyguide_sv.auth.exception;

import org.bodyguide_sv.common.errorHandler.CustomException;
import org.bodyguide_sv.common.errorHandler.ErrorCode;
import org.bodyguide_sv.common.errorHandler.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackages = "org.bodyguide_sv.auth")
public class AuthExceptionHandler {
    
    // 인증 오류
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(CustomException ex) {
        log.error("Authentication error", ex);
        ErrorCode errorCode = ex.getErrorCode();
        ErrorResponse response = new ErrorResponse(errorCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
