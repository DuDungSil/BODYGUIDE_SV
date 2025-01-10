package org.bodyguide_sv.exercise.repository;

import java.util.Optional;

import org.bodyguide_sv.exercise.entity.UsersExerciseBestScore;
import org.bodyguide_sv.exercise.entity.UsersExerciseBestScore.UsersExerciseBestScoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExerciseBestScoreRepository
        extends JpaRepository<UsersExerciseBestScore, UsersExerciseBestScoreId> {

	Optional<UsersExerciseBestScore> findById(UsersExerciseBestScoreId id);
        
}
