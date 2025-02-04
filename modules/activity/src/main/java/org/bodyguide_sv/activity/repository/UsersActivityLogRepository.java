package org.bodyguide_sv.activity.repository;

import org.bodyguide_sv.activity.entity.UsersActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersActivityLogRepository extends JpaRepository<UsersActivityLog, Long> {
    
}
