package org.bodyguide_sv.user.service;

import java.util.UUID;

import org.bodyguide_sv.user.entity.UsersSocialToken;
import org.bodyguide_sv.user.repository.UsersSocialTokenRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSocialTokenService {
    
    private final UsersSocialTokenRepository usersSocialTokenRepository;

    // db 생성
    public void createUserSocialToken(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null");
        }

        UsersSocialToken usersProviderToken = UsersSocialToken.create(userId);

        usersSocialTokenRepository.save(usersProviderToken);
    }

    // 리프레시 토큰 가져오기
    public String getSocialRefreshToken(UUID userId) {

        UsersSocialToken usersProviderToken = usersSocialTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User provider token not found with user_id: " + userId));
            
        return usersProviderToken.getRefreshToken();
    }

    // 리프레시 토큰 업데이트
    public void updateRefreshToken(UUID userId, String newRefreshToken) {
        UsersSocialToken usersProviderToken = usersSocialTokenRepository.findByUserId(userId)
                .orElseGet(() -> UsersSocialToken.create(userId));

        usersProviderToken.setRefreshToken(newRefreshToken);
        usersSocialTokenRepository.save(usersProviderToken);
    }

    // 리프레시 토큰 제거
    public void deleteRefreshToken(UUID userId) {
        UsersSocialToken usersProviderToken = usersSocialTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User provider token not found with user_id: " + userId));

        usersProviderToken.setRefreshToken(null);
        usersSocialTokenRepository.save(usersProviderToken);
    }

}
