package org.hepi.hepi_sv.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.hepi.hepi_sv.user.entity.UsersNutritionProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersNutritionProfileRepository extends JpaRepository<UsersNutritionProfile, Long> {
    
    Optional<UsersNutritionProfile> findByUserId(UUID userId);

}
