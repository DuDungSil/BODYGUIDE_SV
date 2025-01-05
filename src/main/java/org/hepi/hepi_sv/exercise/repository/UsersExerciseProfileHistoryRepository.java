package org.hepi.hepi_sv.exercise.repository;

import org.hepi.hepi_sv.exercise.entity.UsersExerciseStatsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExerciseProfileHistoryRepository extends JpaRepository<UsersExerciseStatsHistory, Long> {
    
}
