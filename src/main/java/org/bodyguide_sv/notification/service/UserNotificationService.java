package org.bodyguide_sv.notification.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bodyguide_sv.notification.controller.response.UserNotificationResponse;
import org.bodyguide_sv.notification.dto.UserNotificationDTO;
import org.bodyguide_sv.notification.entity.UsersNotification;
import org.bodyguide_sv.notification.entity.UsersNotificationHistory;
import org.bodyguide_sv.notification.enums.NotificationTemplate;
import org.bodyguide_sv.notification.enums.NotificationType;
import org.bodyguide_sv.notification.repository.UsersNotificationHistoryRepository;
import org.bodyguide_sv.notification.repository.UsersNotificationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserNotificationService {
    
    private final UsersNotificationRepository usersNotificationRepository;
    private final UsersNotificationHistoryRepository usersNotificationHistoryRepository;

    // 유저 알림 생성
    public void createUserNotification(UUID receiverId, UUID senderId, NotificationType type, NotificationTemplate template,
            Map<String, String> placeholders) {

        // 알림 템플릿을 이용해 메시지 생성
        String message = template.generateMessage(placeholders);

        UsersNotification notification = UsersNotification.builder()
                .receiverId(receiverId)
                .senderId(senderId)
                .content(message)
                .type(type)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plus(type.getTtl()))
                .build();

        usersNotificationRepository.save(notification);
    }

    // 유저 알림 조회 ( 만료된 알림 history 이동 )
    public UserNotificationResponse getActiveNotifications(UUID receiverId) {
        // 모든 알림 조회
        List<UsersNotification> allNotifications = usersNotificationRepository.findAllByReceiverId(receiverId);
    
        // 현재 시간 기준으로 만료된 알림과 유효한 알림 분리
        LocalDateTime now = LocalDateTime.now();
        List<UsersNotification> expiredNotifications = allNotifications.stream()
                .filter(notification -> notification.getExpiresAt().isBefore(now))
                .collect(Collectors.toList());
    
        List<UsersNotification> activeNotifications = allNotifications.stream()
                .filter(notification -> notification.getExpiresAt().isAfter(now))
                .collect(Collectors.toList());
    
        // 만료된 알림을 비동기로 처리
        moveExpiredNotificationsToHistory(expiredNotifications);
    
        // 유효한 알림을 DTO로 변환 (isRead는 false 상태 유지)
        List<UserNotificationDTO> userNotificationDTOs = activeNotifications.stream()
                .map(notification -> UserNotificationDTO.builder()
                        .id(notification.getId())
                        .receiverId(notification.getReceiverId())
                        .senderId(notification.getSenderId())
                        .content(notification.getContent())
                        .type(notification.getType())
                        .isRead(notification.getIsRead()) 
                        .createdAt(notification.getCreatedAt())
                        .expiresAt(notification.getExpiresAt())
                        .build())
                .collect(Collectors.toList());
    
        // 알림 상태를 읽음으로 업데이트 (비동기 처리)
        markNotificationsAsRead(activeNotifications);
    
        // 변환된 DTO 리스트를 UserNotificationResponse에 담아 반환
        return new UserNotificationResponse(userNotificationDTOs);
    }

    // 유저 알림 삭제 ( history 이동 )
    public void deleteNotification(Long notificationId) {

		// 알림 조회
		UsersNotification notification = usersNotificationRepository.findById(notificationId)
						.orElseThrow(() -> new IllegalArgumentException(
										"Notification not found with ID: " + notificationId));

		// 알림을 History로 이동
		UsersNotificationHistory historyNotification = UsersNotificationHistory.builder()
						.id(notification.getId())
						.receiverId(notification.getReceiverId())
						.senderId(notification.getSenderId())
						.content(notification.getContent())
						.type(notification.getType())
						.isRead(notification.getIsRead())
						.readAt(notification.getReadAt())
						.createdAt(notification.getCreatedAt())
						.expiresAt(notification.getExpiresAt())
						.build();
		usersNotificationHistoryRepository.save(historyNotification);

		// 알림 삭제
		usersNotificationRepository.delete(notification);

    }

    @Async
	private void markNotificationsAsRead(List<UsersNotification> activeNotifications) {
        // 읽음 처리
        activeNotifications.forEach(UsersNotification::markAsRead);
		usersNotificationRepository.saveAll(activeNotifications);
    }

    // 만료된 알림 삭제
    @Async
    private void moveExpiredNotificationsToHistory(List<UsersNotification> expiredNotifications) {
        List<UsersNotificationHistory> historyNotifications = expiredNotifications.stream()
                .map(notification -> UsersNotificationHistory.builder()
                        .id(notification.getId())
                        .receiverId(notification.getReceiverId())
                        .senderId(notification.getSenderId())
                        .content(notification.getContent())
                        .type(notification.getType())
                        .isRead(notification.getIsRead())
                        .readAt(notification.getReadAt())
                        .createdAt(notification.getCreatedAt())
                        .expiresAt(notification.getExpiresAt())
                        .build())
                .collect(Collectors.toList());

        usersNotificationHistoryRepository.saveAll(historyNotifications);
        usersNotificationRepository.deleteAll(expiredNotifications);
    }
}
