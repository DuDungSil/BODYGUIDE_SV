package org.bodyguide_sv.exercise.event.listener;

import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.dto.UpdatedBigThreeWeightDTO;
import org.bodyguide_sv.exercise.dto.UpdatedExersiseIds;
import org.bodyguide_sv.exercise.dto.UpdatedMuscleScoreDTO;
import org.bodyguide_sv.exercise.event.ExerciseRecordChangedWithIdsEvent;
import org.bodyguide_sv.exercise.service.ExerciseBestScoreService;
import org.bodyguide_sv.exercise.service.UserBigThreeProfileService;
import org.bodyguide_sv.exercise.service.UserExerciseMuscleScoreProfileService;
import org.bodyguide_sv.exercise.service.UserExerciseTotalPerformanceService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExerciseBestScoreUpdateListener {

    private final ExerciseBestScoreService exerciseBestScoreService;
    private final UserBigThreeProfileService userBigThreeProfileService;
    private final UserExerciseMuscleScoreProfileService userExerciseMuscleScoreProfileService;
    private final UserExerciseTotalPerformanceService userExerciseTotalPerformanceService;

    @Async
    @EventListener
    public void handleExerciseRecordChangedEvent(ExerciseRecordChangedWithIdsEvent event) {
        UUID userId = event.getUserId();

        List<Integer> changedExerciseIdList = event.getChangedExerciseIdList();

        // 비동기 갱신 작업 처리
        System.out.println("운동 기록 변경 이벤트 for userId: " + userId);
        System.out.println("Changed Exercise IDs: " + changedExerciseIdList);

        // BestScore 갱신 
        UpdatedExersiseIds updatedExersiseIds = exerciseBestScoreService.updateBestScore(userId, changedExerciseIdList);

        List<Integer> updatedWeightAndScoreExerIds = updatedExersiseIds.updatedWeightAndScoreExerIds();
        List<Integer> updatedScoreIds = updatedExersiseIds.updatedScoreIds();

        // UsersBigThreeProfile 갱신
        UpdatedBigThreeWeightDTO updatedBigThreeWeightDTO = userBigThreeProfileService.updateBigThreeProfile(userId, updatedWeightAndScoreExerIds);

        // UsersExerciseMuscleScoreProfile 갱신
        UpdatedMuscleScoreDTO updatedMuscleScoreDTO = userExerciseMuscleScoreProfileService.updateMuscleScoreProfile(userId, updatedScoreIds);

        // UsersExerciseTotalPerformance 갱신
        userExerciseTotalPerformanceService.updateTotalPerformance(userId, updatedBigThreeWeightDTO, updatedMuscleScoreDTO);
    }

}
