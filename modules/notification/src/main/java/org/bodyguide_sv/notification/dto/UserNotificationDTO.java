package org.bodyguide_sv.notification.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.notification.enums.NotificationType;

import lombok.Builder;

@Builder
public record UserNotificationDTO(
    Long id,
    UUID receiverId,
    UUID senderId,
    String content,
    NotificationType type,
    Boolean isRead,
    LocalDateTime createdAt,
    LocalDateTime expiresAt
){
    
}
