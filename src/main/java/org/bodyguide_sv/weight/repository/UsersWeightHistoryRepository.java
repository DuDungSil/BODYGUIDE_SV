package org.bodyguide_sv.weight.repository;

import java.util.UUID;

import org.bodyguide_sv.weight.entity.UsersWeightHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersWeightHistoryRepository extends JpaRepository<UsersWeightHistory, Long> {
    
    boolean existsByHistoryIdAndUserId(Long historyId, UUID userId);

    Slice<UsersWeightHistory> findByUserId(UUID userId, Pageable pageable);

}
