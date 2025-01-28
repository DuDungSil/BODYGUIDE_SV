package org.bodyguide_sv.auth.service;

import java.util.UUID;

import org.bodyguide_sv.auth.controller.request.InitializeRequest;
import org.bodyguide_sv.auth.controller.response.TokenResponse;
import org.bodyguide_sv.user.service.UserProfileService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InitializeService {
    
    private final TokenService tokenService;
    private final UserProfileService userProfileService;

    // 계정 초기화 ( 온보딩 )
    // GUEST -> USER
    public TokenResponse initialize(UUID userId, InitializeRequest request) {
        
        // 프로필 입력
        userProfileService.initializeUserProfile(userId, request);

        // 유저 권한 상승 후 새 토큰 반환
        TokenResponse tokenResponse = tokenService.upgradeUserRoleWithToken(userId);

        return tokenResponse;
    }

}
