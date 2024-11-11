package org.hepi.hepi_sv.user.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hepi.hepi_sv.user.entity.UsersMeta;
import org.hepi.hepi_sv.user.repository.jpa.UsersMetaRepository;
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
                .lastUpdatedAt(dateTime)
                .build();

        usersMetaRepository.save(usersMeta);
    }

    public void updateUsersMetaProfile() {

        // 필드를 받아서 업데이트?
    
    }

}
