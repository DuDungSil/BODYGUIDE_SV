package org.hepi.hepi_sv.activity.repository;

import java.util.Optional;
import java.util.UUID;

import org.hepi.hepi_sv.activity.entity.UsersActivityProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersActivityProfileRepository extends JpaRepository<UsersActivityProfile, Long> {
    
    // 유저 ID(UUID)로 프로필 조회
    Optional<UsersActivityProfile> findByUserId(UUID userId);

}
