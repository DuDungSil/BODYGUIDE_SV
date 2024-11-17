package org.hepi.hepi_sv.common.errorHandler;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // auth
    ILLEGAL_REGISTRATION_PROVIDER(HttpStatus.NOT_ACCEPTABLE, "잘못된 social povider 입니다."),
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "토큰을 DB에서 찾을 수 없습니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_MISMATCHED(HttpStatus.UNAUTHORIZED, "토큰이 일치하지 않습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 시그니처입니다."),

    // user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저가 존재하지 않습니다."),
                    

    // default
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request."), // 잘못된 요청
    UNAUTHORIZED_REQUEST(HttpStatus.UNAUTHORIZED, "Unauthorized."), // 인증되지 않은 사용자의 요청
    FORBIDDEN_ACCESS(HttpStatus.FORBIDDEN, "Forbidden."), // 권한이 없는 사용자의 요청
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not found."), // 리소스를 찾을 수 없음
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Not allowed method."), // 허용되지 않은 Request Method 호출
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server error."); // 내부 서버 오류


    private final HttpStatus httpStatus;
    private final String message;
}