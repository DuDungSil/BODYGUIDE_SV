package org.hepi.hepi_sv.notification.dto;

import java.util.List;

public record UserNotificationResponse(
    List<UserNotificationDTO> UserNotifications
){
    
}
