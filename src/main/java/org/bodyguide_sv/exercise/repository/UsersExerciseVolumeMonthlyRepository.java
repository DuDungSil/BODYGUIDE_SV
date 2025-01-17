package org.bodyguide_sv.exercise.repository;

import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.exercise.entity.UsersExerciseVolumeMonthly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExerciseVolumeMonthlyRepository extends JpaRepository<UsersExerciseVolumeMonthly, Long> {
    
    // 특정 사용자, 연도, 월에 해당하는 데이터 조회
    Optional<UsersExerciseVolumeMonthly> findByUserIdAndYearAndMonth(UUID userId, int year, int month);

}
