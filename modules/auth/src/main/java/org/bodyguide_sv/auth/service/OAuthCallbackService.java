package org.bodyguide_sv.auth.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import org.bodyguide_sv.common.event.UserLoginEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthCallbackService {
    
    private final ApplicationEventPublisher eventPublisher;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson 객체 생성

    // 소셜 로그인 성공 리디렉션
    public void processCallback(String accessToken, String refreshToken, HttpServletResponse response) {
        try {
            // JSON 페이로드 생성 (Jackson을 사용하여 안전한 변환)
            String jsonPayload = createJsonPayload(accessToken, refreshToken);

            // 리디렉션 URL 생성 (Base64 인코딩 적용)
            String redirectUri = createRedirectUri(jsonPayload);

            // 비즈니스 로직 처리
            handleOAuthCallback(accessToken, refreshToken);

            // 리디렉션 처리
            response.sendRedirect(redirectUri);
        } catch (JsonProcessingException e) {
            log.error("JSON 변환 중 오류 발생", e);
            throw new RuntimeException("JSON 변환 중 오류가 발생했습니다.", e);
        } catch (IOException e) {
            log.error("OAuth Callback 처리 중 I/O 오류 발생", e);
            throw new RuntimeException("OAuth Callback 처리 중 I/O 오류 발생", e);
        } catch (IllegalArgumentException e) {
            log.error("OAuth Callback 처리 중 잘못된 데이터 오류 발생", e);
            throw new RuntimeException("OAuth Callback 처리 중 잘못된 데이터가 제공되었습니다.", e);
        } catch (Exception e) {
            log.error("OAuth Callback 처리 중 예상치 못한 오류 발생", e);
            throw new RuntimeException("OAuth Callback 처리 중 알 수 없는 오류 발생", e);
        }
    }

    // Jackson을 사용하여 안전한 JSON 생성
    private String createJsonPayload(String accessToken, String refreshToken) throws JsonProcessingException {
        Payload payload = new Payload(accessToken, refreshToken);
        return objectMapper.writeValueAsString(payload); // JSON 직렬화
    }

    // Base64 인코딩을 사용하여 URL 안전성을 보장
    private String createRedirectUri(String jsonPayload) {
        String base64Payload = Base64.getUrlEncoder().encodeToString(jsonPayload.getBytes(StandardCharsets.UTF_8));
        return "bodyguide://oauth2redirect?jsonPayload=" + base64Payload;
    }

    private void handleOAuthCallback(String accessToken, String refreshToken) {
        UUID userId = tokenService.getUserIdFromAccessToken(accessToken);
        eventPublisher.publishEvent(new UserLoginEvent(userId));
    }

    // 내부 DTO 클래스 (JSON 변환용)
    private static class Payload {
        private final String accessToken;
        private final String refreshToken;

        public Payload(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public String getRefreshToken() {
            return refreshToken;
        }
    }
}
