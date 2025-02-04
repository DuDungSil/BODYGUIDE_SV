package org.bodyguide_sv.notification.event;

import java.util.Map;
import java.util.UUID;

import org.bodyguide_sv.notification.enums.NotificationTemplate;
import org.bodyguide_sv.notification.enums.NotificationType;

import lombok.Getter;

@Getter
public class NotificationSendEvent {
    
    private final UUID receiverId;
    private final UUID senderId;
    private final NotificationType type;
    private final NotificationTemplate template;
    private final Map<String, String> placeholders;

    public NotificationSendEvent(UUID receiverId, UUID senderId, NotificationType type, NotificationTemplate template, Map<String, String> placeholders) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.type = type;
        this.template = template;
        this.placeholders = placeholders;
    }

}
