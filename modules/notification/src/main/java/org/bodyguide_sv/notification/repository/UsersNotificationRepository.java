package org.bodyguide_sv.notification.repository;

import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.notification.entity.UsersNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersNotificationRepository extends JpaRepository<UsersNotification, Long> {
    List<UsersNotification> findAllByReceiverId(UUID receiverId);
}
