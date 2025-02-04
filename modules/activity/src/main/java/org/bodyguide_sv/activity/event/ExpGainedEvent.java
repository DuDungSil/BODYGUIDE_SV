package org.bodyguide_sv.activity.event;

import java.util.UUID;

import org.bodyguide_sv.activity.enums.ActivityType;

public class ExpGainedEvent {
    
    private final UUID userId;       // 경험치를 얻는 사용자 ID
    private final ActivityType activityType; // 활동 이름 enum

    public ExpGainedEvent(UUID userId, ActivityType activityType) {
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
