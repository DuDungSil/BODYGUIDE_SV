package org.hepi.hepi_sv.notification.event.listener;

import java.util.Map;
import java.util.UUID;

import org.hepi.hepi_sv.notification.enums.NotificationTemplate;
import org.hepi.hepi_sv.notification.enums.NotificationType;
import org.hepi.hepi_sv.notification.event.NotificationSendEvent;
import org.hepi.hepi_sv.notification.service.UserNotificationService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NotificationListener {
    
    private final UserNotificationService notificationService;

    @EventListener
    public void handleNotificationSendEvent(NotificationSendEvent event) {
        UUID receiverId = event.getReceiverId();
        UUID senderId = event.getSenderId();
        NotificationType type = event.getType();
        NotificationTemplate template = event.getTemplate();
        Map<String, String> placeholders = event.getPlaceholders();

        // 유저 알림 생성
        notificationService.createUserNotification(receiverId, senderId, type, template, placeholders);
    }

}
