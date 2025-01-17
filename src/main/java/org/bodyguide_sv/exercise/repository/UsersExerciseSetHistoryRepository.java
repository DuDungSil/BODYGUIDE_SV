package org.bodyguide_sv.exercise.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.dto.MaxWeightAndRepsDTO;
import org.bodyguide_sv.exercise.entity.UsersExerciseSetHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;

@Repository
public interface UsersExerciseSetHistoryRepository extends JpaRepository<UsersExerciseSetHistory, Long> {
    
    @Transactional
    @Modifying
    @Query("DELETE FROM UsersExerciseSetHistory u WHERE u.userId = :userId AND u.exerciseDate = :exerciseDate AND u.groupId = :groupId")
    void deleteByUserIdAndExerciseDateAndGroupId(
        @Param("userId") UUID userId,
        @Param("exerciseDate") LocalDateTime exerciseDate,
        @Param("groupId") int groupId
    );

    @Query("SELECT MAX(u.groupId) FROM UsersExerciseSetHistory u " +
           "WHERE u.userId = :userId AND FUNCTION('DATE', u.exerciseDate) = FUNCTION('DATE', :exerciseDate)")
    Integer findMaxGroupIdByUserIdAndExerciseDate(
        @Param("userId") UUID userId,
        @Param("exerciseDate") LocalDateTime exerciseDate
    );


    @Query("SELECT DISTINCT u.exerciseId FROM UsersExerciseSetHistory u " +
           "WHERE u.userId = :userId AND u.exerciseDate = :exerciseDate AND u.groupId = :groupId")
    List<Integer> findExerciseIdsByUserIdAndExerciseDateAndGroupId(
        @Param("userId") UUID userId,
        @Param("exerciseDate") LocalDateTime exerciseDate,
        @Param("groupId") int groupId
    );

    @Query("SELECT new org.bodyguide_sv.exercise.dto.MaxWeightAndRepsDTO(h.weight, h.reps) " +
        "FROM UsersExerciseSetHistory h " +
        "WHERE h.userId = :userId AND h.exerciseId = :exerciseId " +
        "AND h.weight = (SELECT MAX(h2.weight) FROM UsersExerciseSetHistory h2 " +
        "                WHERE h2.userId = :userId AND h2.exerciseId = :exerciseId)")
    MaxWeightAndRepsDTO findMaxWeightAndRepsByUserIdAndExerciseId(
        @Param("userId") UUID userId,
        @Param("exerciseId") int exerciseId
    );

    @Query("SELECT MAX(h.score) FROM UsersExerciseSetHistory h " +
       "WHERE h.userId = :userId AND h.exerciseId = :exerciseId")
    Double findMaxScoreByUserIdAndExerciseId(
        @Param("userId") UUID userId,
        @Param("exerciseId") int exerciseId
    );

    // date로 검색
    @Query(value = """
            SELECT * FROM USERS_EXERCISE_SET_HISTORY 
            WHERE user_id = :userId
            AND DATE(exercise_date) = :exerciseDate """, nativeQuery = true)
    List<UsersExerciseSetHistory> findByUserIdAndExerciseDate(UUID userId,
            @Param("exerciseDate") LocalDate exerciseDate);

    // 년, 주로 검색
    @Query(value = """
            SELECT * FROM USERS_EXERCISE_SET_HISTORY 
            WHERE user_id = :userId
            AND YEAR(exercise_date) = :year
            AND WEEK(exercise_date, 1) = :week """, nativeQuery = true)
    List<UsersExerciseSetHistory> findByUserIdAndYearAndWeek(
        @Param("userId") UUID userId,
        @Param("year") int year,
        @Param("week") int week
    );

    // 년, 월로 검색
    @Query(value = """
            SELECT * FROM USERS_EXERCISE_SET_HISTORY 
            WHERE user_id = :userId
            AND YEAR(exercise_date) = :year
            AND MONTH(exercise_date) = :month """, nativeQuery = true)
    List<UsersExerciseSetHistory> findByUserIdAndYearAndMonth(
        @Param("userId") UUID userId,
        @Param("year") int year,
        @Param("month") int month
);

}
