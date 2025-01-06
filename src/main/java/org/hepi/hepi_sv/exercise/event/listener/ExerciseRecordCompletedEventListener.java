package org.hepi.hepi_sv.exercise.event.listener;

import org.hepi.hepi_sv.exercise.event.ExerciseRecordCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExerciseRecordCompletedEventListener {
    
    @EventListener
    public void handleExerciseRecordCompletedEvent(ExerciseRecordCompletedEvent event) {

        // 액티비티 완료 이벤트 발생

        // 최고 운동 프로필 업데이트 ( 갱신시 : 통합기록 테이블 업데이트 )

        // 3대 운동 프로필 업데이트


    }

}
