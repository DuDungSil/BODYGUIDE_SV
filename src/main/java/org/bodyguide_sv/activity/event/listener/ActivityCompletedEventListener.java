package org.bodyguide_sv.activity.event.listener;

import java.util.UUID;

import static org.bodyguide_sv.activity.enums.ActivityType.EXERCISE;
import static org.bodyguide_sv.activity.enums.ActivityType.WEIGHT;
import org.bodyguide_sv.activity.service.ActivityService;
import org.bodyguide_sv.exercise.event.NewExerciseRecordSavedEvent;
import org.bodyguide_sv.weight.event.NewWeightRecordSavedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ActivityCompletedEventListener {
    
    private final ActivityService activityService;

    @Async
    @EventListener
    public void handleNewExerciseRecordCompleted(NewExerciseRecordSavedEvent event) {
        UUID userId = event.getUserId();

        // 운동 기록 액티비티 완료 처리
        activityService.processActivityCompleted(userId, EXERCISE);
    }

    @Async
    @EventListener
    public void handleNewWeightRecordCompleted(NewWeightRecordSavedEvent event) {
        UUID userId = event.getUserId();

        // 체중 기록 액티비티 완료 처리
        activityService.processActivityCompleted(userId, WEIGHT);
    }

}
