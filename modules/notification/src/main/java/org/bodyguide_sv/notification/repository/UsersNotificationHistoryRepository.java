package org.bodyguide_sv.notification.repository;

import org.bodyguide_sv.notification.entity.UsersNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersNotificationHistoryRepository extends JpaRepository<UsersNotificationHistory, Long> {
    
}
