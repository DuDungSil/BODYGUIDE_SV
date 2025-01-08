package org.bodyguide_sv.notification.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bodyguide_sv.notification.entity.SystemNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemNotificationRepository extends JpaRepository<SystemNotification, Integer> {
    // 만료되지 않은 알림 조회
    List<SystemNotification> findAllByExpiresAtAfter(LocalDateTime now);
}
