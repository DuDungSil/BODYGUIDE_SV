package org.bodyguide_sv.auth.service;

import java.util.UUID;

import org.bodyguide_sv.auth.exception.AuthException;
import org.bodyguide_sv.common.enums.SocialProvider;

import static org.bodyguide_sv.common.errorHandler.ErrorCode.ILLEGAL_REGISTRATION_PROVIDER;
import org.bodyguide_sv.user.dto.UserDTO;
import org.bodyguide_sv.user.service.UserService;
import org.bodyguide_sv.user.service.UserSocialTokenService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnlinkService {
    
    private final UserSocialTokenService userSocialTokenService;
    private final LogoutService logoutService;
    private final UserService userService;
    private final KakaoUnlinkService kakaoUnlinkService;
    private final GoogleUnlinkService googleUnlinkService;
    private final AppleUnlinkService appleUnlinkService;

    @Transactional
    public void unlink(UUID userId) {
        // povider, provider id 확인
        UserDTO user = userService.getUserById(userId);
        SocialProvider provider = user.provider();
        String providerId = user.providerId();

        // provider 서비스 연결 끊기
        switch (provider) {
            case GOOGLE -> googleUnlinkService.unlink(userId);
            case KAKAO -> kakaoUnlinkService.unlink(providerId);
            case APPLE -> appleUnlinkService.unlink(userId);
            default -> throw new AuthException(ILLEGAL_REGISTRATION_PROVIDER);
        }

        // 로그아웃
        logoutService.logout(userId);

        // 유저 delete 처리 
        userService.deleteUser(userId);

        // db 소셜 리프레시 토큰 제거
        userSocialTokenService.deleteRefreshToken(userId);
    }

}
