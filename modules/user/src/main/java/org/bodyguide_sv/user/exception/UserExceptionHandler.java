package org.bodyguide_sv.user.exception;

import org.bodyguide_sv.common.errorHandler.ErrorCode;
import org.bodyguide_sv.common.errorHandler.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice(basePackages = "org.bodyguide_sv.user")
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException ex) {
        log.error("User error: {}", ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse(ErrorCode.USER_NOT_FOUND);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}