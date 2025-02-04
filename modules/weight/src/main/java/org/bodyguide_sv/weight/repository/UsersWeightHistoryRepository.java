package org.bodyguide_sv.weight.repository;

import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.weight.entity.UsersWeightHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersWeightHistoryRepository extends JpaRepository<UsersWeightHistory, Long> {
    
    boolean existsByHistoryIdAndUserId(Long historyId, UUID userId);

    Slice<UsersWeightHistory> findByUserId(UUID userId, Pageable pageable);

    // 최신 기록 단일 조회
    @Query("SELECT u FROM UsersWeightHistory u WHERE u.userId = :userId ORDER BY u.recordDate DESC")
    Optional<UsersWeightHistory> findTopByUserIdOrderByRecordDateDesc(UUID userId);

}
