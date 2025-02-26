package org.bodyguide_sv.exercise.service;

import java.util.UUID;

import org.bodyguide_sv.exercise.controller.response.ExerciseTotalPerformanceResponse;
import org.bodyguide_sv.exercise.dto.UpdatedBigThreeWeightDTO;
import org.bodyguide_sv.exercise.dto.UpdatedMuscleScoreDTO;
import org.bodyguide_sv.exercise.entity.UsersExerciseTotalPerformance;
import org.bodyguide_sv.exercise.enums.ExerciseLevel;
import org.bodyguide_sv.exercise.repository.UsersExerciseTotalPerformanceRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserExerciseTotalPerformanceService {

    private final ExerciseAnalysisService exerciseAnalysisService;
    private final UsersExerciseTotalPerformanceRepository usersExerciseTotalPerformanceRepository;

    // 생성
    public void createUserExerciseTotalPerformance(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a exercise profile.");
        }

        UsersExerciseTotalPerformance newProfile = UsersExerciseTotalPerformance.createNewPerformance(userId);

        usersExerciseTotalPerformanceRepository.save(newProfile);

    }

    // Response 가져오기
    public ExerciseTotalPerformanceResponse getTotalPerformanceResponse(UUID userId) {

        // ExerciseTotalPerformance 가져오기
        UsersExerciseTotalPerformance profile = usersExerciseTotalPerformanceRepository.findByUserId(userId)
                .orElseGet(
                        () -> {
                            UsersExerciseTotalPerformance newProfile = UsersExerciseTotalPerformance.createNewPerformance(userId);
                            return usersExerciseTotalPerformanceRepository.save(newProfile);
                        });

        return new ExerciseTotalPerformanceResponse(
                profile.getTotalScore(),
                profile.getScoreUpdatedAt(),
                ExerciseLevel.fromId(profile.getExerciseLevel()).getName(),
                profile.getLevelUpdatedAt(),
                profile.getBigThree(),
                profile.getBigThreeUpdatedAt());
    }

    public void updateTotalPerformance(UUID userId, UpdatedBigThreeWeightDTO updatedBigThreeWeightDTO,
            UpdatedMuscleScoreDTO updatedMuscleScoreDTO) {

        // ExerciseTotalPerformance 가져오기
        UsersExerciseTotalPerformance profile = usersExerciseTotalPerformanceRepository.findByUserId(userId)
                .orElseGet(
                        () -> {
                            UsersExerciseTotalPerformance newProfile = UsersExerciseTotalPerformance.createNewPerformance(userId);
                            return usersExerciseTotalPerformanceRepository.save(newProfile);
                        });

        // 3대운동 업데이트
        if (updatedBigThreeWeightDTO != null) {
            double bigThreeSum = updatedBigThreeWeightDTO.squatWeight() + updatedBigThreeWeightDTO.deadLiftWeight()
                    + updatedBigThreeWeightDTO.benchPressWeight();
            profile.updateBigThree(bigThreeSum);
        }

        // 총 운동 점수 업데이트
        double newTotalScore = (updatedMuscleScoreDTO.coreScore()
                + updatedMuscleScoreDTO.lowerBodyScore()
                + updatedMuscleScoreDTO.backScore()
                + updatedMuscleScoreDTO.chestScore()
                + updatedMuscleScoreDTO.shoulderScore()
                + updatedMuscleScoreDTO.armScore()) / 6;
        profile.updateTotalScore(newTotalScore);

        // 운동 수준 업데이트
        profile.updateExerciseLevel(exerciseAnalysisService.getLevel(newTotalScore).getId());

        usersExerciseTotalPerformanceRepository.save(profile);
    }

}
