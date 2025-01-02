package org.hepi.hepi_sv.notification.dto;

import java.time.LocalDateTime;

public record SystemNotificationDTO(
    int id,
    String content,
    String url,
    LocalDateTime createdAt,
    LocalDateTime expiresAt
) {
    
}
