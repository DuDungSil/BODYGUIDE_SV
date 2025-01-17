package org.bodyguide_sv.exercise.event.listener;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.exercise.event.ExerciseRecordChangedWithDateEvent;
import org.bodyguide_sv.exercise.service.ExerciseVolumeService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExerciseVolumeUpdateListener {
    
    private final ExerciseVolumeService exerciseVolumeService;

    @Async 
    @EventListener
    public void handleExerciseRecordChangedEvent(ExerciseRecordChangedWithDateEvent event) {
        UUID userId = event.getUserId();
        LocalDateTime date = event.getDate();

        // 비동기 갱신 작업 처리
        System.out.println("운동 기록 변경 이벤트 for userId: " + userId);
        System.out.println("Changed date: " + date);

        // volume 갱신 
        exerciseVolumeService.updateExerciseVolume(userId, date);
    }

}
