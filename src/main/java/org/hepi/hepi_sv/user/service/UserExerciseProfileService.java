package org.hepi.hepi_sv.user.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hepi.hepi_sv.user.dto.UserExerciseProfileDTO;
import org.hepi.hepi_sv.user.dto.UserExerciseProfileRequest;
import org.hepi.hepi_sv.user.dto.UserExerciseProfileResponse;
import org.hepi.hepi_sv.user.entity.UsersExerciseProfile;
import org.hepi.hepi_sv.user.entity.UsersExerciseProfileHistory;
import org.hepi.hepi_sv.user.repository.UsersExerciseProfileHistoryRepository;
import org.hepi.hepi_sv.user.repository.UsersExerciseProfileRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserExerciseProfileService {
    
    private final UsersExerciseProfileRepository profileRepository;
    private final UsersExerciseProfileHistoryRepository profileHistoryRepository;

    // 생성
    public void createUsersExerciseProfile(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a exercise profile.");
        }

        LocalDateTime dateTime = LocalDateTime.now();

        UsersExerciseProfile usersExerciseProfile = UsersExerciseProfile.builder()
                                                    .userId(userId)
                                                    .updatedAt(dateTime)
                                                    .build();

        profileRepository.save(usersExerciseProfile);

    }

    // 업데이트
    public void updateUserExerciseProfile(UUID userId, UserExerciseProfileRequest request) {
        
        // 사용자 ID로 프로필을 찾기
        UsersExerciseProfile existingProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User exercise profile not found with user_id: " + userId));
            
        // 기존 프로필을 히스토리 테이블에 저장
        UsersExerciseProfileHistory history = UsersExerciseProfileHistory.builder()
                .userId(existingProfile.getUserId())
                .benchWeight(existingProfile.getBenchWeight())
                .benchReps(existingProfile.getBenchReps())
                .squatWeight(existingProfile.getSquatWeight())
                .squatReps(existingProfile.getSquatReps())
                .deadWeight(existingProfile.getDeadWeight())
                .deadReps(existingProfile.getDeadReps())
                .overheadWeight(existingProfile.getOverheadWeight())
                .overheadReps(existingProfile.getOverheadReps())
                .pushupReps(existingProfile.getPushupReps())
                .pullupReps(existingProfile.getPullupReps())
                .recordAt(existingProfile.getUpdatedAt())
                .build();
        profileHistoryRepository.save(history);

        // 요청 데이터를 기반으로 필드 업데이트
        existingProfile.setBenchWeight(request.benchWeight());
        existingProfile.setBenchReps(request.benchReps());
        existingProfile.setSquatWeight(request.squatWeight());
        existingProfile.setSquatReps(request.squatReps());
        existingProfile.setDeadWeight(request.deadWeight());
        existingProfile.setDeadReps(request.deadReps());
        existingProfile.setOverheadWeight(request.overheadWeight());
        existingProfile.setOverheadReps(request.overheadReps());
        existingProfile.setPushupReps(request.pushupReps());
        existingProfile.setPullupReps(request.pullupReps());

        // 업데이트된 시간 갱신
        existingProfile.setUpdatedAt(LocalDateTime.now());

        // 수정된 엔티티 저장
        profileRepository.save(existingProfile);

    }

    // 가져오기
    public UserExerciseProfileResponse getUserExerciseProfileResponse(UUID userId) {
        UsersExerciseProfile usersExerciseProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User exercise profile not found with user_id: " + userId));

        return convertToResponse(usersExerciseProfile);
    }

    // 레포트 분석용 DTO 객체 가져오기
    public UserExerciseProfileDTO getUserExerciseProfileDTO(UUID userId) {
        UsersExerciseProfile usersExerciseProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User exercise profile not found with user_id: " + userId));

        return convertToDTO(usersExerciseProfile);
    }

    private UserExerciseProfileResponse convertToResponse(UsersExerciseProfile usersExerciseProfile) {
        return new UserExerciseProfileResponse(
                usersExerciseProfile.getBenchWeight(),
                usersExerciseProfile.getBenchReps(),
                usersExerciseProfile.getSquatWeight(),
                usersExerciseProfile.getSquatReps(),
                usersExerciseProfile.getDeadWeight(),
                usersExerciseProfile.getDeadReps(),
                usersExerciseProfile.getOverheadWeight(),
                usersExerciseProfile.getOverheadReps(),
                usersExerciseProfile.getPushupReps(),
                usersExerciseProfile.getPullupReps());
    }

    private UserExerciseProfileDTO convertToDTO(UsersExerciseProfile usersExerciseProfile) {
        return new UserExerciseProfileDTO(
                usersExerciseProfile.getBenchWeight(),
                usersExerciseProfile.getBenchReps(),
                usersExerciseProfile.getSquatWeight(),
                usersExerciseProfile.getSquatReps(),
                usersExerciseProfile.getDeadWeight(),
                usersExerciseProfile.getDeadReps(),
                usersExerciseProfile.getOverheadWeight(),
                usersExerciseProfile.getOverheadReps(),
                usersExerciseProfile.getPushupReps(),
                usersExerciseProfile.getPullupReps());
    }
    
}
