package org.hepi.hepi_sv.auth.service;

import java.util.Map;
import java.util.UUID;

import org.hepi.hepi_sv.user.service.UserSocialTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class KakaoUnlinkService {

    private final UserSocialTokenService userProviderTokenService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUrl;

    public void unlink(UUID userId) {
        // 리프레시 토큰 가져오기
        String refreshToken = userProviderTokenService.getRefreshToken(userId);

        if (refreshToken == null) {
            throw new RuntimeException("Refresh token not found for user ID: " + userId);
        }

        // 액세스 토큰 갱신
        String accessToken = getAccessToken(refreshToken);

        // 카카오 API를 통해 연결 끊기
        unlinkFromProvider(accessToken);
    }

    private String getAccessToken(String refreshToken) {
        try {
            // 요청 파라미터 설정
            MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
            requestParams.add("client_id", clientId);
            requestParams.add("client_secret", clientSecret);
            requestParams.add("refresh_token", refreshToken);
            requestParams.add("grant_type", "refresh_token");

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestParams, headers);

            // 토큰 갱신 요청
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("access_token");
            } else {
                throw new RuntimeException("Failed to refresh access token: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error occurred while refreshing access token", e);
        }
    }

    private void unlinkFromProvider(String accessToken) {
        try {
            // 카카오 Unlink API URL
            String unlinkUrl = "https://kapi.kakao.com/v1/user/unlink";

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken); // Bearer 토큰 설정

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            // API 요청
            ResponseEntity<String> response = restTemplate.postForEntity(unlinkUrl, requestEntity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Successfully unlinked from Kakao.");
            } else {
                throw new RuntimeException("Failed to unlink provider: " + response.getStatusCode());
            }

        } catch (Exception e) {
            throw new RuntimeException("Error occurred while unlinking provider", e);
        }
    }
}
