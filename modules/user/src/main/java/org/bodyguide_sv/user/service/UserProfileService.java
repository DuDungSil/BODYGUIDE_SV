package org.bodyguide_sv.user.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.user.controller.request.UserProfileRequest;
import org.bodyguide_sv.user.controller.response.UserProfileResponse;
import org.bodyguide_sv.user.dto.InitializeProfileDTO;
import org.bodyguide_sv.user.dto.UserProfileDTO;
import org.bodyguide_sv.user.entity.UsersProfile;
import org.bodyguide_sv.user.repository.UsersProfileRepository;
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

    // 부분 업데이트
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

        // 유저 가입 일자 가져오기
        LocalDateTime registerDateTime = userMetaService.getUserRegisterDateTime(userId);

        // LocalDateTime -> LocalDate 변환
        LocalDate registerDate = registerDateTime.toLocalDate();

        return convertToResponse(usersProfile, registerDate);
    }

    // 프로필 초기화 ( 온보딩 )
    public void initializeUserProfile(UUID userId, InitializeProfileDTO profileDTO) {

        // 1. 기존 프로필 조회
        UsersProfile usersProfile = usersProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found with user_id: " + userId));

        // 2. 프로필 필드 업데이트
        usersProfile.setNickname(profileDTO.nickname());
        usersProfile.setGender(profileDTO.gender());
        usersProfile.setHeight(profileDTO.height());
        usersProfile.setWeight(profileDTO.weight());
        usersProfile.setBirthDate(profileDTO.birthDate());
        usersProfile.setUpdatedAt(LocalDateTime.now());

        usersProfileRepository.save(usersProfile);

        // 3. 메타 Source 필드 업데이트
        userMetaService.updateSource(userId, profileDTO.source());

    }

    // 레포트 분석용 DTO 객체 가져오기
    public UserProfileDTO getUserProfileDTO(UUID userId) {
        UsersProfile usersProfile = usersProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User profile not found with user_id: " + userId));

        return convertToDTO(usersProfile);
    }

    private UserProfileResponse convertToResponse(UsersProfile usersProfile, LocalDate registerDate) {
        return new UserProfileResponse(
                usersProfile.getNickname(),
                usersProfile.getGender(),
                usersProfile.getHeight(),
                usersProfile.getWeight(),
                usersProfile.getBirthDate(),
                registerDate,
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
