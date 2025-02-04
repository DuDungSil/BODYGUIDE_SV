package org.bodyguide_sv.exercise.repository;

import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.exercise.entity.UsersExerciseVolumeWeekly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExerciseVolumeWeeklyRepository extends JpaRepository<UsersExerciseVolumeWeekly, Long> {
    
    // 특정 사용자, 연도, 주차에 해당하는 데이터 조회
    Optional<UsersExerciseVolumeWeekly> findByUserIdAndYearAndWeek(UUID userId, int year, int week);

    // 페이지 조회
    Page<UsersExerciseVolumeWeekly> findByUserId(UUID userId, Pageable pageable);

}
