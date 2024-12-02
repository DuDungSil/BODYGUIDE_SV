package org.hepi.hepi_sv.user.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hepi.hepi_sv.auth.dto.InitializeRequest;
import org.hepi.hepi_sv.user.dto.UserProfileDTO;
import org.hepi.hepi_sv.user.dto.UserProfileRequest;
import org.hepi.hepi_sv.user.dto.UserProfileResponse;
import org.hepi.hepi_sv.user.entity.UsersProfile;
import org.hepi.hepi_sv.user.repository.UsersProfileRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    
    private final UsersProfileRepository usersProfileRepository;
    private final UserMetaService userMetaService;

    // 테이블 생성
    public void createUserProfile(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a profile.");
        }

        UsersProfile usersProfile = UsersProfile.builder()
                .userId(userId)
                .build();

        usersProfileRepository.save(usersProfile);
    }

    // 업데이트
    public void updateUserProfile(UUID userId, UserProfileRequest request) {

        // 1. 기존 프로필 조회
        UsersProfile usersProfile = usersProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found with user_id: " + userId));

        // 2. null이 아닌 필드만 업데이트
        if (request.nickname() != null) {
            usersProfile.setNickname(request.nickname());
        }
        if (request.gender() != null) {
            usersProfile.setGender(request.gender());
        }
        if (request.height() != 0) { // double 기본값이 0.0
            usersProfile.setHeight(request.height());
        }
        if (request.weight() != 0) {
            usersProfile.setWeight(request.weight());
        }
        if (request.birthDate() != null) {
            usersProfile.setBirthDate(request.birthDate());
            // 나이도 계산 해서 넣어야함
        }
        if (request.profileImg() != null) {
            usersProfile.setProfileImg(request.profileImg());
        }
        if (request.introText() != null) {
            usersProfile.setIntroText(request.introText());
        }

        // 3. 업데이트 시간 기록
        usersProfile.setUpdatedAt(LocalDateTime.now());

        // 4. 저장
        usersProfileRepository.save(usersProfile);
        
    }
    
    // Response 가져오기
    public UserProfileResponse getUserProfileResponse(UUID userId) {
        UsersProfile usersProfile = usersProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found with user_id: " + userId));

        return convertToResponse(usersProfile);
    }
    
    // 프로필 초기화
    public void initializeUserProfile(UUID userId, InitializeRequest request) {

        // 1. 기존 프로필 조회
        UsersProfile usersProfile = usersProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found with user_id: " + userId));

        // 2. 프로필 필드 업데이트
        usersProfile.setNickname(request.nickname());
        usersProfile.setGender(request.gender());
        usersProfile.setHeight(request.height());
        usersProfile.setWeight(request.weight());
        usersProfile.setBirthDate(request.birthDate());
        usersProfile.setUpdatedAt(LocalDateTime.now());

        usersProfileRepository.save(usersProfile);

        // 3. 메타 Source 필드 업데이트
        userMetaService.updateSource(userId, request.Source());

    }
    
    // 레포트 분석용 DTO 객체 가져오기
    public UserProfileDTO getUserProfileDTO(UUID userId) {
        UsersProfile usersProfile = usersProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found with user_id: " + userId));
        
        return convertToDTO(usersProfile);
    }

    private UserProfileResponse convertToResponse(UsersProfile usersProfile) {
        return new UserProfileResponse(
                usersProfile.getNickname(),
                usersProfile.getGender(),
                usersProfile.getHeight(),
                usersProfile.getWeight(),
                usersProfile.getBirthDate(),
                usersProfile.getProfileImg(),
                usersProfile.getIntroText());
    }

    private UserProfileDTO convertToDTO(UsersProfile usersProfile) {
        return new UserProfileDTO(
                usersProfile.getGender(),
                usersProfile.getHeight(),
                usersProfile.getWeight(),
                usersProfile.getBirthDate());
    }


}