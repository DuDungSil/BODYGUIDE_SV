package org.hepi.hepi_sv.activity.event.listener;

import java.util.UUID;

import org.hepi.hepi_sv.activity.enums.ActivityType;
import org.hepi.hepi_sv.activity.event.ActivityCompletedEvent;
import org.hepi.hepi_sv.activity.service.ActivityService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ActivityCompletedEventListener {
    
    private final ActivityService activityService;

    @EventListener
    public void handleActivityCompleted(ActivityCompletedEvent event) {
        UUID userId = event.getUserId();
        ActivityType activityType = event.getActivityType();

        // 액티비티 완료 처리
        activityService.processActivityCompleted(userId, activityType);
    }

}
