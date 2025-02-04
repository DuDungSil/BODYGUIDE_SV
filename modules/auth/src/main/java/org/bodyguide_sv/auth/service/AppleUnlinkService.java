package org.bodyguide_sv.auth.service;

import java.util.UUID;

import org.bodyguide_sv.user.service.UserSocialTokenService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AppleUnlinkService {
    
    private final UserSocialTokenService userProviderTokenService;

    public void unlink(UUID userId) {

        // 리프레시 토큰 get
        String socialRefreshToken = userProviderTokenService.getSocialRefreshToken(userId);

        // 프로바이더로부터 액세스토큰 get
        String socialAccessToken = getSocialAccessToken(socialRefreshToken);

        // 애플 탈퇴
        unlinkFromApple(socialAccessToken);

    }

    private String getSocialAccessToken(String socialRefreshToken) {
        return "";
    }


    private void unlinkFromApple(String accessToken) {

    }

}
