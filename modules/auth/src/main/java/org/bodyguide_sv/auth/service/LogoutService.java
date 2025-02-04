package org.bodyguide_sv.auth.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService {
    
    private final TokenService tokenService;

    public void logout(UUID userId) {
        // redis 리프레시 토큰 제거
        tokenService.deleteRefreshToken(userId);

    }

}
