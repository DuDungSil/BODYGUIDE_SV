package org.bodyguide_sv.notification.controller.response;

import java.util.List;

import org.bodyguide_sv.notification.dto.UserNotificationDTO;

public record UserNotificationResponse(
    List<UserNotificationDTO> UserNotifications
){
    
}
