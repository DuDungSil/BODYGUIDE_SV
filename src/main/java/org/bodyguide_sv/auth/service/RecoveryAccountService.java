package org.bodyguide_sv.auth.service;

import java.util.UUID;

import org.bodyguide_sv.auth.controller.response.TokenResponse;
import org.bodyguide_sv.user.dto.UserDTO;
import org.bodyguide_sv.user.service.UserService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecoveryAccountService {
    
    private final TokenService tokenService;
    private final UserService userService;

    public TokenResponse recoveryAccount(UUID userId) {
        
        // 복구
        UserDTO recoveredUser = userService.recoveryUser(userId);

        // response 생성
        TokenResponse tokenResponse = tokenService.generateTokenResponseByUserDTO(recoveredUser);

        return tokenResponse;
    }

}
