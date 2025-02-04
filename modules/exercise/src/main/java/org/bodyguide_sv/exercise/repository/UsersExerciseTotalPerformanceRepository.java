package org.bodyguide_sv.exercise.repository;

import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.exercise.entity.UsersExerciseTotalPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExerciseTotalPerformanceRepository extends JpaRepository<UsersExerciseTotalPerformance, Long> {
    
    // 특정 UserId로 BigThreeProfile 조회
    Optional<UsersExerciseTotalPerformance> findByUserId(UUID userId);

}
