package org.hepi.hepi_sv.experience.event.listener;

import java.util.UUID;

import org.hepi.hepi_sv.experience.enums.ActivityType;
import org.hepi.hepi_sv.experience.event.ExperienceGainedEvent;
import org.hepi.hepi_sv.experience.service.ExpService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExperienceGainedEventListener {
    
    private final ExpService expService;

    @EventListener
    public void handleExperienceGained(ExperienceGainedEvent event) {
        UUID userId = event.getUserId();
        ActivityType activityType = event.getActivityType();

        // 경험치 획득 처리
        expService.processExperienceGain(userId, activityType);
    }

}
