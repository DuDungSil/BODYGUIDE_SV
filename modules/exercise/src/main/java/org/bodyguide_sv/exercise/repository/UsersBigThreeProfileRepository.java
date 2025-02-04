package org.bodyguide_sv.exercise.repository;

import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.exercise.entity.UsersBigThreeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersBigThreeProfileRepository extends JpaRepository<UsersBigThreeProfile, Long> {

    // 특정 UserId로 BigThreeProfile 조회
    Optional<UsersBigThreeProfile> findByUserId(UUID userId);

}
