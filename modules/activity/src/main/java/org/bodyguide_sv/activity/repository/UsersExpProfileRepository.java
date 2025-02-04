package org.bodyguide_sv.activity.repository;

import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.activity.entity.UsersExpProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersExpProfileRepository extends JpaRepository<UsersExpProfile, Long> {
    
    // 사용자 ID로 UsersExpProfile 조회
    Optional<UsersExpProfile> findByUserId(UUID userId);

}
