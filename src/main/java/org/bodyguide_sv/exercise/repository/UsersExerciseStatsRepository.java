package org.bodyguide_sv.exercise.repository;

import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.exercise.entity.UsersExerciseStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExerciseStatsRepository extends JpaRepository<UsersExerciseStats, Long> {

    Optional<UsersExerciseStats> findByUserId(UUID userId);

}