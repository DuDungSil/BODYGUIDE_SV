package org.hepi.hepi_sv.user.service;

import java.util.UUID;

import org.hepi.hepi_sv.user.dto.UserProfileDTO;
import org.hepi.hepi_sv.user.entity.UsersProfile;
import org.hepi.hepi_sv.user.repository.jpa.UsersProfileRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    
    private final UsersProfileRepository usersProfileRepository;

    public void createUserProfile(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a profile.");
        }

        UsersProfile usersProfile = UsersProfile.builder()
                                        .userId(userId)
                                        .build();

        usersProfileRepository.save(usersProfile);
    }

    public void updateUserProfile() {

        // 필드를 받아서 업데이트?
    
    }
    
    public UserProfileDTO getUserProfile(UUID userId) {
        UsersProfile usersProfile = usersProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found with user_id: " + userId));

        return convertToDTO(usersProfile);
    }

    private UserProfileDTO convertToDTO(UsersProfile usersProfile) {
        return new UserProfileDTO(
                usersProfile.getNickname(),
                usersProfile.getGender(),
                usersProfile.getAge(),
                usersProfile.getHeight(),
                usersProfile.getWeight(),
                usersProfile.getBirthDate(),
                usersProfile.getProfileImg(),
                usersProfile.getIntroText()
        );
    }

}