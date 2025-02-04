package org.bodyguide_sv.common.exception;

import org.bodyguide_sv.common.errorHandler.ErrorCode;
import org.bodyguide_sv.common.errorHandler.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class TokenExceptionHandler {
    
    // 토큰 오류
    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(TokenException ex) {
        log.error("Token error: {}", ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.UNAUTHORIZED_REQUEST);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
