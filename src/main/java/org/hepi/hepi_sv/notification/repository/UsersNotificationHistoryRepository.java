package org.hepi.hepi_sv.notification.repository;

import org.hepi.hepi_sv.notification.entity.UsersNotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersNotificationHistoryRepository extends JpaRepository<UsersNotificationHistory, Long> {
    
}
