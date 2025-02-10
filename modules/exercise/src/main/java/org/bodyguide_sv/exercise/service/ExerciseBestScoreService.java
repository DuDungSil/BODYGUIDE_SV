package org.bodyguide_sv.exercise.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bodyguide_sv.exercise.dto.MaxScoreAndWeightAndRepsDTO;
import org.bodyguide_sv.exercise.dto.MaxWeightAndRepsDTO;
import org.bodyguide_sv.exercise.dto.UpdatedExersiseIds;
import org.bodyguide_sv.exercise.entity.UsersExerciseBestScore;
import org.bodyguide_sv.exercise.entity.UsersExerciseBestScore.UsersExerciseBestScoreId;
import org.bodyguide_sv.exercise.repository.UsersExerciseBestScoreRepository;
import org.bodyguide_sv.exercise.repository.custom.UsersExerciseSetHistoryCustomRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExerciseBestScoreService {

    private final UsersExerciseSetHistoryCustomRepository usersExerciseSetHistoryCustomRepository;
    private final UsersExerciseBestScoreRepository usersExerciseBestScoreRepository;

    // BestScore 갱신
    @Transactional
    public UpdatedExersiseIds updateBestScore(UUID userId, List<Integer> changedExerciseIdList) {
        List<Integer> updatedWeightAndRepsIds = new ArrayList<>();
        List<Integer> updatedScoreExerIds = new ArrayList<>();

        for (Integer exerciseId : changedExerciseIdList) {
            // Weight와 Reps 갱신
            if (updateBestWeightAndReps(userId, exerciseId)) {
                updatedWeightAndRepsIds.add(exerciseId);
            }

            // Score 갱신
            if (updateBestScoreForExercise(userId, exerciseId)) {
                updatedScoreExerIds.add(exerciseId);
            }
        }

        // 중복 없이 합치기
        Set<Integer> combinedSet = new HashSet<>();
        combinedSet.addAll(updatedWeightAndRepsIds);
        combinedSet.addAll(updatedScoreExerIds);

        // Set을 List로 변환 
        List<Integer> updatedWeightAndScoreExerIds = new ArrayList<>(combinedSet);

        return new UpdatedExersiseIds(updatedWeightAndScoreExerIds, updatedScoreExerIds);

    }

    private boolean updateBestWeightAndReps(UUID userId, int exerciseId) {
        // DTO로 weight와 reps 가져오기
        MaxWeightAndRepsDTO maxWeightAndReps = usersExerciseSetHistoryCustomRepository.findMaxWeightAndRepsByUserIdAndExerciseId(userId, exerciseId);

        if (maxWeightAndReps == null) {
            return false; // 데이터가 없으면 갱신되지 않음
        }

        // Best Score 엔티티 조회
        UsersExerciseBestScoreId bestScoreId = new UsersExerciseBestScoreId(userId, exerciseId);
        UsersExerciseBestScore bestScore = usersExerciseBestScoreRepository.findById(bestScoreId)
                .orElse(UsersExerciseBestScore.createNew(userId, exerciseId));

        // 기존 값과 비교
        boolean updated = false;
        if (!maxWeightAndReps.weight().equals(bestScore.getWeight()) || maxWeightAndReps.reps() != bestScore.getReps()) {
            bestScore.updateWeightAndReps(maxWeightAndReps.weight(), maxWeightAndReps.reps());
            usersExerciseBestScoreRepository.save(bestScore);
            updated = true; // 갱신됨
        }

        return updated;
    }

    private boolean updateBestScoreForExercise(UUID userId, int exerciseId) {
        // 가장 큰 score 가져오기
        MaxScoreAndWeightAndRepsDTO scoreAndWeightAndReps = usersExerciseSetHistoryCustomRepository.findMaxScoreByUserIdAndExerciseId(userId, exerciseId);

        if (scoreAndWeightAndReps == null) {
            return false; // 데이터가 없으면 갱신되지 않음
        }

        Double scoreWeight = scoreAndWeightAndReps.weight();
        Integer scoreReps = scoreAndWeightAndReps.reps();
        Double maxScore = scoreAndWeightAndReps.score();

        // Best Score 엔티티 조회
        UsersExerciseBestScoreId bestScoreId = new UsersExerciseBestScoreId(userId, exerciseId);
        UsersExerciseBestScore bestScore = usersExerciseBestScoreRepository.findById(bestScoreId)
                .orElse(UsersExerciseBestScore.createNew(userId, exerciseId));

        // 기존 값과 비교
        boolean updated = false;
        if (!maxScore.equals(bestScore.getScore())) {
            bestScore.updateScore(scoreWeight, scoreReps, maxScore);
            usersExerciseBestScoreRepository.save(bestScore);
            updated = true; // 갱신됨
        }

        return updated;
    }

}
