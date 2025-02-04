package org.bodyguide_sv.exercise.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bodyguide_sv.exercise.controller.response.ExerciseBigThreeProfileResponse;
import org.bodyguide_sv.exercise.dto.UpdatedBigThreeWeightDTO;
import org.bodyguide_sv.exercise.entity.UsersBigThreeProfile;
import org.bodyguide_sv.exercise.entity.UsersExerciseBestScore;
import org.bodyguide_sv.exercise.entity.UsersExerciseBestScore.UsersExerciseBestScoreId;
import static org.bodyguide_sv.exercise.enums.MajorExercise.BENCH_PRESS;
import static org.bodyguide_sv.exercise.enums.MajorExercise.DEAD_LIFT;
import static org.bodyguide_sv.exercise.enums.MajorExercise.SQUAT;
import org.bodyguide_sv.exercise.repository.UsersBigThreeProfileRepository;
import org.bodyguide_sv.exercise.repository.UsersExerciseBestScoreRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserBigThreeProfileService {
    
    private final UsersBigThreeProfileRepository usersBigThreeProfileRepository;
    private final UsersExerciseBestScoreRepository usersExerciseBestScoreRepository;
    
    // 생성
    public void createUserBigThreeProfile(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a exercise profile.");
        }

        UsersBigThreeProfile newProfile = UsersBigThreeProfile.createDefaultProfile(userId);

        usersBigThreeProfileRepository.save(newProfile);

    }

    // Response 가져오기
    public ExerciseBigThreeProfileResponse getBigThreeProfileResponse(UUID userId) {

        UsersBigThreeProfile profile = usersBigThreeProfileRepository.findByUserId(userId)
        .orElseGet(
                () -> {
                    UsersBigThreeProfile newProfile = UsersBigThreeProfile.createDefaultProfile(userId);
                    return usersBigThreeProfileRepository.save(newProfile);
                        });
        
        return new ExerciseBigThreeProfileResponse(
                        profile.getSquat().getScore(),
                        profile.getSquat().getWeight(),
                        profile.getSquat().getReps(),
                        profile.getSquat().getUpdatedAt(),
                        profile.getDeadLift().getScore(),
                        profile.getDeadLift().getWeight(),
                        profile.getDeadLift().getReps(),
                        profile.getDeadLift().getUpdatedAt(),
                        profile.getBenchPress().getScore(),
                        profile.getBenchPress().getWeight(),
                        profile.getBenchPress().getReps(),
                        profile.getBenchPress().getUpdatedAt() );
    }

    @Transactional
    public UpdatedBigThreeWeightDTO updateBigThreeProfile(UUID userId, List<Integer> exerciseIdList) {
        Set<Integer> targetIds = Set.of(BENCH_PRESS.getExerId(), SQUAT.getExerId(), DEAD_LIFT.getExerId());
        if (Collections.disjoint(exerciseIdList, targetIds)) {
            return null; 
        }

        // BigThreeProfile 가져오기
        UsersBigThreeProfile profile = usersBigThreeProfileRepository.findByUserId(userId)
                .orElseGet(
                        () -> {
                            UsersBigThreeProfile newProfile = UsersBigThreeProfile.createDefaultProfile(userId);
                            return usersBigThreeProfileRepository.save(newProfile);
                        });
            
        // 필요한 BestScore를 한 번에 가져오기
        Map<Integer, UsersExerciseBestScore> bestScores = usersExerciseBestScoreRepository
            .findAllById(
                targetIds.stream()
                        .map(exerciseId -> new UsersExerciseBestScoreId(userId, exerciseId))
                        .collect(Collectors.toList())
            )
            .stream()
                .collect(Collectors.toMap(score -> score.getId().getExerciseId(), score -> score));

        // Bench Press 업데이트
        if (bestScores.containsKey(BENCH_PRESS.getExerId())) { 
            UsersExerciseBestScore bestScore = bestScores.get(BENCH_PRESS.getExerId());
            profile.updateBenchPress(bestScore.getWeight(), bestScore.getReps(), bestScore.getScore());
        }

        // Squat 업데이트
        if (bestScores.containsKey(SQUAT.getExerId())) {
            UsersExerciseBestScore bestScore = bestScores.get(SQUAT.getExerId());
            profile.updateSquat(bestScore.getWeight(), bestScore.getReps(), bestScore.getScore());
        }

        // Deadlift 업데이트
        if (bestScores.containsKey(DEAD_LIFT.getExerId())) {
            UsersExerciseBestScore bestScore = bestScores.get(DEAD_LIFT.getExerId());
            profile.updateDeadlift(bestScore.getWeight(), bestScore.getReps(), bestScore.getScore());
        }

        UpdatedBigThreeWeightDTO updatedBigThreeWeightDTO = new UpdatedBigThreeWeightDTO(
                                                                        profile.getSquat().getWeight(),
                                                                        profile.getDeadLift().getWeight(),
                                                                        profile.getBenchPress().getWeight());  

        usersBigThreeProfileRepository.save(profile);

        return updatedBigThreeWeightDTO;
    }

}
