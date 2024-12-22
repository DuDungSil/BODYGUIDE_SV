package org.hepi.hepi_sv.experience.repository;

import org.hepi.hepi_sv.experience.entity.UsersActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersActivityLogRepository extends JpaRepository<UsersActivityLog, Long> {
    
}
