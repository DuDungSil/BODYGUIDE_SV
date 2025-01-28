package org.bodyguide_sv.user.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.user.entity.UsersMeta;
import org.bodyguide_sv.user.repository.UsersMetaRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMetaService {
    
    private final UsersMetaRepository usersMetaRepository;

    // 생성
    public void createUsersMeta(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a profile.");
        }

        UsersMeta usersMeta = UsersMeta.create(userId);

        usersMetaRepository.save(usersMeta);
    }

    // 추천 경로 업데이트
    public void updateSource(UUID userId, String source) {

        // 1. 기존 메타 조회
        UsersMeta usersMeta = usersMetaRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found with user_id: " + userId));

        // 2. 필드 업데이트
        usersMeta.setSource(source);

        // 3. 엔티티 저장
        usersMetaRepository.save(usersMeta);

    }
    
    // 마지막 로그인 시간 업데이트
    public void updateLastLoginAt(UUID userId) {
        
        // 1. 기존 메타 조회
        UsersMeta usersMeta = usersMetaRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found with user_id: " + userId));

        // 2. 필드 업데이트
        usersMeta.setLastLoginAt(LocalDateTime.now());

        // 3. 엔티티 저장
        usersMetaRepository.save(usersMeta);

    }

}
