package org.bodyguide_sv.auth.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.bodyguide_sv.auth.event.UserLoginEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthCallbackService {
    
    private final ApplicationEventPublisher eventPublisher;
    private final TokenService tokenService;

    public void processCallback(String accessToken, String refreshToken, HttpServletResponse response) {
        try {
            // JSON 페이로드 생성
            String jsonPayload = createJsonPayload(accessToken, refreshToken);

            // 리디렉션 URL 생성
            String redirectUri = createRedirectUri(jsonPayload);

            // 비즈니스 로직 처리
            handleOAuthCallback(accessToken, refreshToken);

            // 리디렉션 처리
            response.sendRedirect(redirectUri);
        } catch (IOException e) {
            // I/O 관련 오류 처리
            log.error("OAuth Callback 처리 중 I/O 오류 발생", e);
            throw new RuntimeException("OAuth Callback 처리 중 I/O 오류 발생", e);
        } catch (IllegalArgumentException e) {
            // 잘못된 입력 데이터 처리
            log.error("OAuth Callback 처리 중 잘못된 데이터 오류 발생", e);
            throw new RuntimeException("OAuth Callback 처리 중 잘못된 데이터가 제공되었습니다.", e);
        } catch (Exception e) {
            // 기타 예상치 못한 오류 처리
            log.error("OAuth Callback 처리 중 예상치 못한 오류 발생", e);
            throw new RuntimeException("OAuth Callback 처리 중 알 수 없는 오류 발생", e);
        }
    }

    private String createJsonPayload(String accessToken, String refreshToken) {
        return String.format("{\"accessToken\":\"%s\", \"refreshToken\":\"%s\"}", accessToken, refreshToken);
    }

    private String createRedirectUri(String jsonPayload) throws IOException {
        return "bodyguide://oauth2redirect?jsonPayload=" +
                URLEncoder.encode(jsonPayload, StandardCharsets.UTF_8);
    }

    private void handleOAuthCallback(String accessToken, String refreshToken) {
        UUID userId = tokenService.getUserIdFromAccessToken(accessToken);
        eventPublisher.publishEvent(new UserLoginEvent(userId));
    }

}
