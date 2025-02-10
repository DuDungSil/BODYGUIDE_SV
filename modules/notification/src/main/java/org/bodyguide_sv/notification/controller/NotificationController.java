package org.bodyguide_sv.notification.controller;

import java.util.Map;
import java.util.UUID;

import org.bodyguide_sv.notification.controller.response.SystemNotificationResponse;
import org.bodyguide_sv.notification.controller.response.UserNotificationResponse;
import static org.bodyguide_sv.notification.enums.NotificationTemplate.WEIGHT_COTINUOUS_RECORD;
import static org.bodyguide_sv.notification.enums.NotificationType.MESSAGE;
import org.bodyguide_sv.notification.event.NotificationSendEvent;
import org.bodyguide_sv.notification.service.SystemNotificationService;
import org.bodyguide_sv.notification.service.UserNotificationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
@Tag(name = "Notification", description = "알림 관련")
public class NotificationController {

    private final UserNotificationService userNotificationService;
    private final SystemNotificationService systemNotificationService;
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping("/test")
    public ResponseEntity<String> getMethodName(@AuthenticationPrincipal UserDetails userDetails) {

        UUID userId = UUID.fromString(userDetails.getUsername());

        NotificationSendEvent event = new NotificationSendEvent(userId, userId, MESSAGE, WEIGHT_COTINUOUS_RECORD,
                Map.of("date", "6"));
        eventPublisher.publishEvent(event);

        return ResponseEntity.ok("성공");
    }

    // 유저 알림 리스트 가져오기
    @GetMapping("/user")
    public ResponseEntity<UserNotificationResponse> getUserNotification(
            @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        UserNotificationResponse response = userNotificationService.getActiveNotifications(userId);

        return ResponseEntity.ok(response);
    }

    // 유저 알림 삭제
    @DeleteMapping("/user/{notificationId}")
    public ResponseEntity<String> deleteUserNotification(
            @AuthenticationPrincipal UserDetails userDetails, @PathVariable Long notificationId) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        userNotificationService.deleteNotification(userId, notificationId);

        return ResponseEntity.ok("삭제 성공");
    }

    // 시스템 알림 리스트 가져오기
    @GetMapping("/system")
    public ResponseEntity<SystemNotificationResponse> getSystemNotification() {

        SystemNotificationResponse response = systemNotificationService.getActiveSystemNotifications();

        return ResponseEntity.ok(response);
    }

}
