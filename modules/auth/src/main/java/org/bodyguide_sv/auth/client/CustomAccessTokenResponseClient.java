package org.bodyguide_sv.auth.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessTokenResponseClient
        implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    
    private static final Logger logger = LoggerFactory.getLogger(CustomAccessTokenResponseClient.class);

    private final DefaultAuthorizationCodeTokenResponseClient defaultClient = new DefaultAuthorizationCodeTokenResponseClient();

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(OAuth2AuthorizationCodeGrantRequest authorizationCodeGrantRequest) {
        // 기본 Spring Security 클라이언트 호출
        OAuth2AccessTokenResponse response = defaultClient.getTokenResponse(authorizationCodeGrantRequest);
    
        // 새로운 OAuth2AccessTokenResponse 생성
        return OAuth2AccessTokenResponse.withToken(response.getAccessToken().getTokenValue())
                .tokenType(response.getAccessToken().getTokenType())
                .expiresIn(response.getAccessToken().getExpiresAt().getEpochSecond() - System.currentTimeMillis() / 1000)
                .refreshToken(response.getRefreshToken() != null ? response.getRefreshToken().getTokenValue() : null)
                .additionalParameters(response.getAdditionalParameters())
                .build();
    }
}