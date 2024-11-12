package org.hepi.hepi_sv.auth.service;

import java.util.UUID;

import org.hepi.hepi_sv.auth.exception.AuthException;
import static org.hepi.hepi_sv.common.errorHandler.CustomErrorCode.ILLEGAL_REGISTRATION_PROVIDER;
import org.hepi.hepi_sv.user.entity.Users;
import org.hepi.hepi_sv.user.service.UserRegistrationService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnlinkService {
    
    private final UserRegistrationService userRegistrationService;

    @Transactional
    public void unlink(UUID userId) {
        // povider, provider id 확인
        Users user = userRegistrationService.getUsers(userId);
        String provider = user.getProvider();
        String providerId = user.getProviderId();

        // provider 서비스 연결 끊기
        switch (provider) { // registration id별로 userInfo 생성
            case "google" -> ofGoogle();
            case "kakao" -> ofKakao();
            default -> throw new AuthException(ILLEGAL_REGISTRATION_PROVIDER);
        }

        // 유저 기록 del 추가
        userRegistrationService.deletionUser();
    }

    private void ofKakao() {
        // 카카오 - 어드민 id 이용

    }

    private void ofGoogle() {
        // 구글 - 몰루?
    }

}
