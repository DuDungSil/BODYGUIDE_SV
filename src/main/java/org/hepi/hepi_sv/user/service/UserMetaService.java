package org.hepi.hepi_sv.user.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hepi.hepi_sv.user.entity.UsersMeta;
import org.hepi.hepi_sv.user.repository.UsersMetaRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserMetaService {
    
    private final UsersMetaRepository usersMetaRepository;

    public void createUsersMeta(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a profile.");
        }

        LocalDateTime dateTime = LocalDateTime.now();

        UsersMeta usersMeta = UsersMeta.builder()
                .userId(userId)
                .createdAt(dateTime)
                .lastLoginAt(dateTime)
                .updatedAt(dateTime)
                .isDelete(false)
                .build();

        usersMetaRepository.save(usersMeta);
    }

    public void updateSource(UUID userId, String source) {

        // 1. 기존 메타 조회
        UsersMeta usersMeta = usersMetaRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found with user_id: " + userId));
        
        // 2. 필드 업데이트
        usersMeta.setSource(source);

        // 3. 엔티티 저장
        usersMetaRepository.save(usersMeta);

    }

}
