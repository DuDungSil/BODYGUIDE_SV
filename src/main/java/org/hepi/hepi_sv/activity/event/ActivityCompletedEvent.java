package org.hepi.hepi_sv.activity.event;

import java.util.UUID;

import org.hepi.hepi_sv.activity.enums.ActivityType;

import lombok.Getter;

@Getter
public class ActivityCompletedEvent {

    private final UUID userId;       // 활동을 완료한 사용자 ID
    private final ActivityType activityType; // 활동 이름 enum

    public ActivityCompletedEvent(UUID userId, ActivityType activityType) {
        this.userId = userId;
        this.activityType = activityType;
    }

}
