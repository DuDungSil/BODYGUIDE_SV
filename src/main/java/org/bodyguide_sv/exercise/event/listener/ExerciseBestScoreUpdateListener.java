package org.bodyguide_sv.exercise.event.listener;

import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.event.ExerciseBestScoreChangedEvent;
import org.bodyguide_sv.exercise.event.ExerciseRecordChangedEvent;
import org.bodyguide_sv.exercise.service.ExerciseBestScoreService;
import org.bodyguide_sv.exercise.service.UserBigThreeProfileService;
import org.bodyguide_sv.exercise.service.UserExerciseMuscleScoreProfileService;
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

    @Async 
    @EventListener
    public void handleExerciseRecordChangedEvent(ExerciseRecordChangedEvent event) {
        UUID userId = event.getUserId();
        List<Integer> changedExerciseIdList = event.getChangedExerciseIdList();

        // 비동기 갱신 작업 처리
        System.out.println("운동 기록 변경 이벤트 for userId: " + userId);
        System.out.println("Changed Exercise IDs: " + changedExerciseIdList);

        // 갱신 로직 구현
        exerciseBestScoreService.updateBestScore(userId, changedExerciseIdList);
    }

    @EventListener
    public void handleExerciseBestScoreChangedEventEvent(ExerciseBestScoreChangedEvent event) {
        UUID userId = event.getUserId();
        List<Integer> updatedWeightAndScoreExerIds = event.getUpdatedWeightAndScoreExerIds();
        List<Integer> updatedScoreIds = event.getUpdatedScoreIds();

        // 비동기 갱신 작업 처리
        System.out.println("베스트 점수 변경 이벤트 for userId: " + userId);
        System.out.println("Changed Exercise IDs: " + updatedWeightAndScoreExerIds);
        System.out.println("Changed Exercise IDs: " + updatedScoreIds);

        // UsersBigThreeProfile 갱신
        userBigThreeProfileService.updateBigThreeProfile(userId, updatedWeightAndScoreExerIds);

        // UsersExerciseMuscleScoreProfile 갱신
        userExerciseMuscleScoreProfileService.updateMuscleScoreProfile(userId, updatedScoreIds);

        // 

    }

}