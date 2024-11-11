package org.hepi.hepi_sv.user.repository.jpa;

import java.util.Optional;
import java.util.UUID;

import org.hepi.hepi_sv.user.entity.UsersProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersProfileRepository extends JpaRepository<UsersProfile, Long> {
    
    Optional<UsersProfile> findByUserId(UUID userId);

}
