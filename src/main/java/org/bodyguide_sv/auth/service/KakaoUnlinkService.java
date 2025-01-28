package org.bodyguide_sv.auth.service;

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

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${kakao.admin-key}")
    private String adminKey;

    @Value("${kakao.unlink-uri}")
    private String unlinkUrl;

    public void unlink(String providerId) {

        // 카카오 API를 통해 연결 끊기 ( 어드민 키 이용 )
        unlinkFromKakao(providerId);

    }

    private void unlinkFromKakao(String providerId) {
        try {

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Authorization", "KakaoAK " + adminKey); // KakaoAK 키 설정

            // HTTP 요청 본문 설정
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("target_id_type", "user_id");
            body.add("target_id", providerId);

            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

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
