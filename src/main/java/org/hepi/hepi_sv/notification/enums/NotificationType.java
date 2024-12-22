package org.hepi.hepi_sv.notification.enums;

import java.time.Duration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    MESSAGE(1, Duration.ofDays(3), "일반 메시지");

    private final int code;
    private final Duration ttl;   
    private final String description;

    public int getCode() {
        return this.code;
    }

    public Duration getTtl() {
        return this.ttl;
    }

    public static NotificationType fromCode(int code) {
        for (NotificationType type : NotificationType.values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid NotificationType code: " + code);
    }

}
