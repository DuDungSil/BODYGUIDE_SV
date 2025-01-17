package org.bodyguide_sv.notification.controller.response;

import java.util.List;

import org.bodyguide_sv.notification.dto.SystemNotificationDTO;

public record SystemNotificationResponse(
    List<SystemNotificationDTO> systemNotifications
) {
   
}
