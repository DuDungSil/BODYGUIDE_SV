package org.bodyguide_sv.exercise.repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.exercise.entity.UsersExerciseVolumeDaily;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExerciseVolumeDailyRepository extends JpaRepository<UsersExerciseVolumeDaily, Long> {
    
    // 특정 사용자와 날짜에 해당하는 데이터 조회
    Optional<UsersExerciseVolumeDaily> findByUserIdAndDate(UUID userId, LocalDate date);

    // 페이지 조회
    Page<UsersExerciseVolumeDaily> findByUserId(UUID userId, Pageable pageable);

}
