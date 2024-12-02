package org.hepi.hepi_sv.user.repository;

import org.hepi.hepi_sv.user.entity.UsersExerciseProfileHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExerciseProfileHistoryRepository extends JpaRepository<UsersExerciseProfileHistory, Long> {
    
}
