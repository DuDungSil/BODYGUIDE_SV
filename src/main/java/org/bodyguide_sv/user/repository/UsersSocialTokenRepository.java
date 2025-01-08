package org.bodyguide_sv.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.user.entity.UsersSocialToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersSocialTokenRepository extends JpaRepository<UsersSocialToken, Long> {
    
    Optional<UsersSocialToken> findByUserId(UUID userId);

}
 