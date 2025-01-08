package org.bodyguide_sv.nutrition.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.nutrition.dto.UserNutritionProfileDTO;
import org.bodyguide_sv.nutrition.dto.UserNutritionProfileRequest;
import org.bodyguide_sv.nutrition.dto.UserNutritionProfileResponse;
import org.bodyguide_sv.nutrition.entity.UsersNutritionProfile;
import org.bodyguide_sv.nutrition.entity.UsersNutritionProfileHistory;
import org.bodyguide_sv.nutrition.repository.UsersNutritionProfileHistoryRepository;
import org.bodyguide_sv.nutrition.repository.UsersNutritionProfileRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserNutritionProfileService {
    
    private final UsersNutritionProfileRepository profileRepository;
    private final UsersNutritionProfileHistoryRepository profileHistoryRepository;

    // 생성
    public void createUsersNutritionProfile(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a nutrition profile.");
        }
    
        // UsersNutritionProfile의 create 메서드 사용
        UsersNutritionProfile usersNutritionProfile = UsersNutritionProfile.create(userId);
    
        profileRepository.save(usersNutritionProfile);
    }

    // 업데이트
    public void updateUserNutritionProfile(UUID userId, UserNutritionProfileRequest request) {

        // 사용자 ID로 프로필을 찾기
        UsersNutritionProfile existingProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User nutrition profile not found with user_id: " + userId));

        // 기존 프로필을 히스토리 테이블에 저장
        UsersNutritionProfileHistory history = UsersNutritionProfileHistory.builder()
                .userId(existingProfile.getUserId())
                .pa(existingProfile.getPa())
                .dietId(existingProfile.getDietId())
                .targetWeight(existingProfile.getTargetWeight())
                .targetCalory(existingProfile.getTargetCalory())
                .carbCal(existingProfile.getCarbCal())
                .proteinCal(existingProfile.getProteinCal())
                .fatCal(existingProfile.getFatCal())
                .wakeupTime(existingProfile.getWakeupTime())
                .sleepTime(existingProfile.getSleepTime())
                .recordAt(existingProfile.getUpdatedAt())
                .build();
        profileHistoryRepository.save(history);

        // 요청 데이터를 기반으로 필드 업데이트 (null 체크 추가)
        if (request.pa() != 0) {
            existingProfile.setPa(request.pa());
        }
        if (request.dietId() != 0) {
            existingProfile.setDietId(request.dietId());
        }
        if (request.targetWeight() != null) {
            existingProfile.setTargetWeight(request.targetWeight());
        }
        if (request.targetCalory() != null) {
            existingProfile.setTargetCalory(request.targetCalory());
        }
        if (request.carbCal() != null) {
            existingProfile.setCarbCal(request.carbCal());
        }
        if (request.proteinCal() != null) {
            existingProfile.setProteinCal(request.proteinCal());
        }
        if (request.fatCal() != null) {
            existingProfile.setFatCal(request.fatCal());
        }
        if (request.wakeupTime() != null) {
            existingProfile.setWakeupTime(request.wakeupTime());
        }
        if (request.sleepTime() != null) {
            existingProfile.setSleepTime(request.sleepTime());
        }
        
        // 업데이트된 시간 갱신
        existingProfile.setUpdatedAt(LocalDateTime.now());

        // 수정된 엔티티 저장
        profileRepository.save(existingProfile);

    }

    // 가져오기
    public UserNutritionProfileResponse getUserNutritionProfileResponse(UUID userId) {
        UsersNutritionProfile usersNutritionProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User nutrition profile not found with user_id: " + userId));

        return convertToResponse(usersNutritionProfile);
    }

    // 레포트 분석용 DTO 객체 가져오기
    public UserNutritionProfileDTO getUserNutritionProfileDTO(UUID userId) {
        UsersNutritionProfile usersNutritionProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User nutrition profile not found with user_id: " + userId));

        return convertToDTO(usersNutritionProfile);
    }

    private UserNutritionProfileResponse convertToResponse(UsersNutritionProfile usersNutritionProfile) {
        return new UserNutritionProfileResponse(
                usersNutritionProfile.getPa(),
                usersNutritionProfile.getDietId(),
                usersNutritionProfile.getTargetWeight(),
                usersNutritionProfile.getTargetCalory(),
                usersNutritionProfile.getCarbCal(),
                usersNutritionProfile.getProteinCal(),
                usersNutritionProfile.getFatCal(),
                usersNutritionProfile.getWakeupTime(),
                usersNutritionProfile.getSleepTime());
    }

    private UserNutritionProfileDTO convertToDTO(UsersNutritionProfile usersNutritionProfile) {
        return new UserNutritionProfileDTO(
                usersNutritionProfile.getPa(),
                usersNutritionProfile.getDietId(),
                usersNutritionProfile.getTargetWeight(),
                usersNutritionProfile.getTargetCalory(),
                usersNutritionProfile.getCarbCal(),
                usersNutritionProfile.getProteinCal(),
                usersNutritionProfile.getFatCal(),
                usersNutritionProfile.getWakeupTime(),
                usersNutritionProfile.getSleepTime()
        );
    }

}
