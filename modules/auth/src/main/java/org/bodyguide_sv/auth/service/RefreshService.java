package org.bodyguide_sv.auth.service;

import java.util.UUID;

import org.bodyguide_sv.auth.controller.request.TokenRequest;
import org.bodyguide_sv.auth.controller.response.TokenResponse;
import org.bodyguide_sv.common.event.UserRefreshEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshService {
    
    private final ApplicationEventPublisher eventPublisher;
    private final TokenService tokenService;

    public TokenResponse tokenRefresh(TokenRequest request) {
        
        TokenResponse tokenResponse = tokenService.reissueTokenResponse(request);

        UUID userId = tokenService.getUserIdFromAccessToken(tokenResponse.accessToken());

        // 로그인 기록
        eventPublisher.publishEvent(new UserRefreshEvent(userId));

        return tokenResponse;
    }
    
}
