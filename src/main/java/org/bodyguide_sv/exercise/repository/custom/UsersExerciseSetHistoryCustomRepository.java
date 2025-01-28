package org.bodyguide_sv.exercise.repository.custom;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.dto.MaxScoreAndWeightAndRepsDTO;
import org.bodyguide_sv.exercise.dto.MaxWeightAndRepsDTO;
import org.bodyguide_sv.exercise.entity.UsersExerciseSetHistory;

public interface UsersExerciseSetHistoryCustomRepository {
    
    MaxWeightAndRepsDTO findMaxWeightAndRepsByUserIdAndExerciseId(UUID userId, int exerciseId);

    MaxScoreAndWeightAndRepsDTO findMaxScoreByUserIdAndExerciseId(UUID userId, int exerciseId);

    List<UsersExerciseSetHistory> findByUserIdAndExerciseDate(UUID userId, LocalDate exerciseDate);

    List<UsersExerciseSetHistory> findByUserIdAndYearAndWeek(UUID userId, int year, int week);

    List<UsersExerciseSetHistory> findByUserIdAndYearAndMonth(UUID userId, int year, int month);

}
