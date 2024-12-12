package org.hepi.hepi_sv.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.hepi.hepi_sv.user.entity.UsersMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMetaRepository extends JpaRepository<UsersMeta, Long> {
    
    Optional<UsersMeta> findByUserId(UUID userId);

}
