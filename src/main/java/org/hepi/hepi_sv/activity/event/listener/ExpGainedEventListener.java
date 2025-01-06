package org.hepi.hepi_sv.activity.event.listener;

import java.util.UUID;

import org.hepi.hepi_sv.activity.enums.ActivityType;
import org.hepi.hepi_sv.activity.event.ExpGainedEvent;
import org.hepi.hepi_sv.activity.service.ExpService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExpGainedEventListener {
    
    private final ExpService expService;

    @EventListener
    public void handleActivityCompleted(ExpGainedEvent event) {
        UUID userId = event.getUserId();
        ActivityType activityType = event.getActivityType();

        // 경험치 획득 처리
        expService.processExperienceGain(userId, activityType);
    }


}
