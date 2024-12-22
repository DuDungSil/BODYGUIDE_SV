package org.hepi.hepi_sv.notification.dto;

import java.util.List;

public record SystemNotificationResponse(
    List<SystemNotificationDTO> systemNotifications
) {
   
}
