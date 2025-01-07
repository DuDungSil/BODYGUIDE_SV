package org.hepi.hepi_sv.exercise.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hepi.hepi_sv.exercise.dto.UserExerciseStatsDTO;
import org.hepi.hepi_sv.exercise.dto.UserExerciseStatsRequest;
import org.hepi.hepi_sv.exercise.dto.UserExerciseStatsResponse;
import org.hepi.hepi_sv.exercise.entity.UsersExerciseStats;
import org.hepi.hepi_sv.exercise.entity.UsersExerciseStatsHistory;
import org.hepi.hepi_sv.exercise.repository.UsersExerciseProfileHistoryRepository;
import org.hepi.hepi_sv.exercise.repository.UsersExerciseProfileRepository;
import org.hepi.hepi_sv.exercise.vo.ExerciseStats;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserExerciseStatsService {
    
    private final UsersExerciseProfileRepository profileRepository;
    private final UsersExerciseProfileHistoryRepository profileHistoryRepository;

    // 생성
    public void createUsersExerciseProfile(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a exercise profile.");
        }
        
        UsersExerciseStats usersExerciseStats = UsersExerciseStats.create(userId);

        profileRepository.save(usersExerciseStats);

    }

    // 업데이트
    public void updateUserExerciseProfile(UUID userId, UserExerciseStatsRequest request) {
        // 사용자 ID로 프로필을 찾기
        UsersExerciseStats existingProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User exercise profile not found with user_id: " + userId));

        // 기존 프로필을 히스토리 테이블에 저장
        saveProfileHistory(existingProfile);

        // request → ExerciseStats
        ExerciseStats requestStats = convertToExerciseStats(request);

        // 요청 데이터를 기반으로 프로필 업데이트
        existingProfile.updateWithStats(requestStats);

        // 수정된 엔티티 저장
        profileRepository.save(existingProfile);
    }

    // 히스토리에 저장
    private void saveProfileHistory(UsersExerciseStats profile) {
        UsersExerciseStatsHistory history = UsersExerciseStatsHistory.builder()
                .userId(profile.getUserId())
                .exerciseStats(profile.getExerciseStats()) // 운동 데이터를 통째로 저장
                .recordAt(profile.getUpdatedAt())
                .build();
        profileHistoryRepository.save(history);
    }

    // 가져오기
    public UserExerciseStatsResponse getUserExerciseProfileResponse(UUID userId) {
        UsersExerciseStats usersExerciseProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User exercise profile not found with user_id: " + userId));

        return convertToResponse(usersExerciseProfile);
    }

    // 레포트 분석용 DTO 객체 가져오기
    public UserExerciseStatsDTO getUserExerciseProfileDTO(UUID userId) {
        UsersExerciseStats usersExerciseProfile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User exercise profile not found with user_id: " + userId));

        return convertToDTO(usersExerciseProfile);
    }

    // 요청 DTO → ExerciseStats 변환
    private ExerciseStats convertToExerciseStats(UserExerciseStatsRequest request) {
        return ExerciseStats.builder()
                .benchWeight(request.benchWeight())
                .benchReps(request.benchReps())
                .squatWeight(request.squatWeight())
                .squatReps(request.squatReps())
                .deadWeight(request.deadWeight())
                .deadReps(request.deadReps())
                .overheadWeight(request.overheadWeight())
                .overheadReps(request.overheadReps())
                .pushupReps(request.pushupReps())
                .pullupReps(request.pullupReps())
                .build();
    }

    // 엔티티 → 응답 DTO 변환
    private UserExerciseStatsResponse convertToResponse(UsersExerciseStats usersExerciseStats) {
        ExerciseStats stats = usersExerciseStats.getExerciseStats();
        return UserExerciseStatsResponse.builder()
                .benchWeight(stats.getBenchWeight())
                .benchReps(stats.getBenchReps())
                .squatWeight(stats.getSquatWeight())
                .squatReps(stats.getSquatReps())
                .deadWeight(stats.getDeadWeight())
                .deadReps(stats.getDeadReps())
                .overheadWeight(stats.getOverheadWeight())
                .overheadReps(stats.getOverheadReps())
                .pushupReps(stats.getPushupReps())
                .pullupReps(stats.getPullupReps())
                .build();
    }

    // 엔티티 → DTO 변환
    private UserExerciseStatsDTO convertToDTO(UsersExerciseStats usersExerciseStats) {
        ExerciseStats stats = usersExerciseStats.getExerciseStats();
        return UserExerciseStatsDTO.builder()
                .benchWeight(stats.getBenchWeight())
                .benchReps(stats.getBenchReps())
                .squatWeight(stats.getSquatWeight())
                .squatReps(stats.getSquatReps())
                .deadWeight(stats.getDeadWeight())
                .deadReps(stats.getDeadReps())
                .overheadWeight(stats.getOverheadWeight())
                .overheadReps(stats.getOverheadReps())
                .pushupReps(stats.getPushupReps())
                .pullupReps(stats.getPullupReps())
                .build();
    }
    
}
