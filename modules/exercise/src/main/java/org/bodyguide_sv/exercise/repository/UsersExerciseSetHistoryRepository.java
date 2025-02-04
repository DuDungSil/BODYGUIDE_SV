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

@Repository
public interface UsersExerciseSetHistoryRepository extends JpaRepository<UsersExerciseSetHistory, Long> {
    
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

}
