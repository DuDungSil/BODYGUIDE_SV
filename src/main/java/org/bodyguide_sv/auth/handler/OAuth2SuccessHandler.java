package org.bodyguide_sv.auth.handler;

import java.util.UUID;

import org.bodyguide_sv.auth.controller.response.TokenResponse;
import org.bodyguide_sv.auth.service.TokenService;
import org.bodyguide_sv.user.service.UserSocialTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    
    private final TokenService tokenService;
    private final UserSocialTokenService userSocialTokenService;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException, java.io.IOException {
                
        if (authentication instanceof OAuth2AuthenticationToken authToken) {

            // 사용자 ID 추출 (OAuth2AuthenticationToken의 이름 또는 속성에서 가져오기)
            String userIdString = authToken.getName(); // 일반적으로 OAuth2 인증의 "sub" (사용자 고유 ID)
            UUID userId = UUID.fromString(userIdString);

            // 클라이언트 ID와 사용자를 기반으로 OAuth2AuthorizedClient 가져오기
            OAuth2AuthorizedClient authorizedClient =
                    authorizedClientService.loadAuthorizedClient(
                            authToken.getAuthorizedClientRegistrationId(),
                            authToken.getName()
                    );

            // 리프레시 토큰 가져오기
            String socialRefreshToken = authorizedClient.getRefreshToken().getTokenValue();

            // 필요 시 DB 저장
            userSocialTokenService.updateRefreshToken(userId, socialRefreshToken);

            // TokenResponse 생성
            TokenResponse tokenResponse = tokenService.generateTokenResponse(authentication);

            // 클라이언트가 요청할 URL 설정
            String redirectUrl = String.format(
                    "/auth/callback?access_token=%s&refresh_token=%s",
                    tokenResponse.accessToken(),
                    tokenResponse.refreshToken()
            );

            // 리다이렉션
            response.sendRedirect(redirectUrl);

        } else {
            throw new IllegalArgumentException("Authentication is not OAuth2AuthenticationToken");
        }
    }
}
