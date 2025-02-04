package org.bodyguide_sv.auth.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecoveryCallbackService {
    
    private final ObjectMapper objectMapper = new ObjectMapper(); // Jackson 객체 생성

    // 계정 복구 페이지 리디렉션
    public void processCallback(String accessToken, HttpServletResponse response) {
        try {
            // JSON 페이로드 생성 (Jackson을 사용하여 안전한 변환)
            String jsonPayload = createJsonPayload(accessToken);

            // 리디렉션 URL 생성 (Base64 인코딩 적용)
            String redirectUri = createRedirectUri(jsonPayload);

            // 비즈니스 로직 처리
            handleOAuthCallback(accessToken);

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
    private String createJsonPayload(String accessToken) throws JsonProcessingException {
        Payload payload = new Payload(accessToken);
        return objectMapper.writeValueAsString(payload); // JSON 직렬화
    }

    // Base64 인코딩을 사용하여 URL 안전성을 보장
    private String createRedirectUri(String jsonPayload) {
        String base64Payload = Base64.getUrlEncoder().encodeToString(jsonPayload.getBytes(StandardCharsets.UTF_8));
        return "bodyguide://recoveryRedirect?jsonPayload=" + base64Payload;
    }

    private void handleOAuthCallback(String accessToken) {
        // 추가 로직 (로그 저장, 세션 처리 등)
    }

    // 내부 DTO 클래스 (JSON 변환용)
    private static class Payload {
        private final String accessToken;

        public Payload(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getAccessToken() {
            return accessToken;
        }
    }
}