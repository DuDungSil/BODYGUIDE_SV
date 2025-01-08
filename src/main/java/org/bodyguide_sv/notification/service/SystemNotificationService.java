package org.bodyguide_sv.notification.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.bodyguide_sv.notification.dto.SystemNotificationDTO;
import org.bodyguide_sv.notification.dto.SystemNotificationResponse;
import org.bodyguide_sv.notification.entity.SystemNotification;
import org.bodyguide_sv.notification.repository.SystemNotificationRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SystemNotificationService {
    
    private final SystemNotificationRepository systemNotificationRepository;

    public SystemNotificationResponse getActiveSystemNotifications() {
        // 현재 시간 기준으로 만료되지 않은 알림 조회
        LocalDateTime now = LocalDateTime.now();
        List<SystemNotification> activeNotifications = systemNotificationRepository.findAllByExpiresAtAfter(now);

        // DTO로 변환
        List<SystemNotificationDTO> notificationDTOs = activeNotifications.stream()
                .map(notification -> new SystemNotificationDTO(
                        notification.getId(),
                        notification.getContent(),
                        notification.getUrl(),
                        notification.getCreatedAt(),
                        notification.getExpiresAt()
                ))
                .collect(Collectors.toList());

        // Response 생성 및 반환
        return new SystemNotificationResponse(notificationDTOs);
    }

}
