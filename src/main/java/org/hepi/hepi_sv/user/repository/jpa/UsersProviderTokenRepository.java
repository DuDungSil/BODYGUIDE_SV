package org.hepi.hepi_sv.user.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.hepi.hepi_sv.user.entity.UsersProviderToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersProviderTokenRepository extends JpaRepository<UsersProviderToken, Long> {
    
    Optional<UsersProviderToken> findByUserId(UUID userId);

}
 