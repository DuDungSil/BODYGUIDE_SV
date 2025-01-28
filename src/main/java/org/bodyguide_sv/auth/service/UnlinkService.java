package org.bodyguide_sv.auth.service;

import java.util.UUID;

import org.bodyguide_sv.auth.enums.SocialProvider;
import org.bodyguide_sv.auth.exception.AuthException;
import static org.bodyguide_sv.common.errorHandler.ErrorCode.ILLEGAL_REGISTRATION_PROVIDER;
import org.bodyguide_sv.user.entity.Users;
import org.bodyguide_sv.user.service.UserRegistrationService;
import org.bodyguide_sv.user.service.UserService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnlinkService {
    
    private final UserService usernService;
    private final UserRegistrationService userRegistrationService;
    private final KakaoUnlinkService kakaoUnlinkService;
    private final GoogleUnlinkService googleUnlinkService;

    @Transactional
    public void unlink(UUID userId) {
        // povider, provider id 확인
        Users user = usernService.getUserById(userId);
        SocialProvider provider = user.getProvider();
        String providerId = user.getProviderId();

        // provider 서비스 연결 끊기
        switch (provider) { 
            case GOOGLE -> googleUnlinkService.unlink(userId);
            case KAKAO -> kakaoUnlinkService.unlink(providerId);
            default -> throw new AuthException(ILLEGAL_REGISTRATION_PROVIDER);
        }

        // 유저 기록 del 추가
        userRegistrationService.deletionUser();
    }

}
