package org.bodyguide_sv.exercise.repository;

import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.exercise.entity.UsersMuscleScoreProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMuscleScoreProfileRepository extends JpaRepository<UsersMuscleScoreProfile, Long> {
    
    // 특정 UserId로 UsersMuscleScoreProfile 조회
    Optional<UsersMuscleScoreProfile> findByUserId(UUID userId);

}
