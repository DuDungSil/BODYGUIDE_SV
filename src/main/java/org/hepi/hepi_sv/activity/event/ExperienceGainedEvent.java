package org.hepi.hepi_sv.activity.event;

import java.util.UUID;

import org.hepi.hepi_sv.activity.enums.ActivityType;

public class ExperienceGainedEvent {

    private final UUID userId;       // 경험치를 얻는 사용자 ID
    private final ActivityType activityType; // 활동 이름 enum

    public ExperienceGainedEvent(UUID userId, ActivityType activityType) {
        this.userId = userId;
        this.activityType = activityType;
    }

    public UUID getUserId() {
        return userId;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

}
