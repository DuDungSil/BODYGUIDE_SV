package org.hepi.hepi_sv.user.service;

import java.util.UUID;

import org.hepi.hepi_sv.user.entity.UsersProviderToken;
import org.hepi.hepi_sv.user.repository.jpa.UsersProviderTokenRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProviderTokenService {
    
    private final UsersProviderTokenRepository usersProviderTokenRepository;

    // db 생성
    public void createUserProviderToken(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null");
        }

        UsersProviderToken usersProviderToken = UsersProviderToken.builder()
                                        .userId(userId)
                                        .build();

        usersProviderTokenRepository.save(usersProviderToken);
    }

    // 리프레시 토큰 가져오기
    public String getRefreshToken(UUID userId) {

        UsersProviderToken usersProviderToken = usersProviderTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User provider token not found with user_id: " + userId));
            
        return usersProviderToken.getRefreshToken();
    }

    // 리프레시 토큰 업데이트
    public void updateRefreshToken(UUID userId, String newRefreshToken) {
        UsersProviderToken usersProviderToken = usersProviderTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User provider token not found with user_id: " + userId));

        usersProviderToken.setRefreshToken(newRefreshToken);
        usersProviderTokenRepository.save(usersProviderToken);
    }

    // 리프레시 토큰 제거
    public void deleteRefreshToken(UUID userId) {
        UsersProviderToken usersProviderToken = usersProviderTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User provider token not found with user_id: " + userId));

        usersProviderToken.setRefreshToken(null);
        usersProviderTokenRepository.save(usersProviderToken);
    }

}
