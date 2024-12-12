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
public class GoogleUnlinkService {
    
    private final UserSocialTokenService userProviderTokenService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String  tokenUrl;

    public void unlink(UUID userId) {
        // 리프레시 토큰 get
        String refreshToken = userProviderTokenService.getRefreshToken(userId);
        // 예외처리 1. refreshToken이 null일 경우
        //         2. 유효기간이 지난경우                   => 재인증 필요

        // 프로바이더로부터 액세스토큰 get
        String AccessToken = getAccessToken(refreshToken);

        // 탈퇴
        unlinkFromProvider(AccessToken);
    }
    
    private String getAccessToken(String refreshToken) {
        try {
            // HTTP 요청을 생성 (Apache HttpClient, RestTemplate, 또는 WebClient를 사용할 수 있음)
            RestTemplate restTemplate = new RestTemplate();

            // 요청 파라미터 설정
            MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
            requestParams.add("client_id", clientId); // Google 클라이언트 ID
            requestParams.add("client_secret", clientSecret); // Google 클라이언트 Secret
            requestParams.add("refresh_token", refreshToken); // 리프레시 토큰
            requestParams.add("grant_type", "refresh_token");

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestParams, headers);

            // Google OAuth2 토큰 갱신 요청
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // 액세스 토큰 추출
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
            // Google Unlink URL
            String unlinkUrl = "https://accounts.google.com/o/oauth2/revoke";
    
            // HTTP 요청을 생성
            RestTemplate restTemplate = new RestTemplate();
    
            // 요청 파라미터 설정
            String urlWithParams = unlinkUrl + "?token=" + accessToken;
    
            // Google OAuth2 unlink 요청
            ResponseEntity<String> response = restTemplate.getForEntity(urlWithParams, String.class);
    
            if (response.getStatusCode().is2xxSuccessful()) {
                // 성공적으로 unlink 완료
                System.out.println("Unlink successful for access token: " + accessToken);
            } else {
                throw new RuntimeException("Failed to unlink provider: " + response.getStatusCode());
            }
    
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while unlinking provider", e);
        }
    }

}
