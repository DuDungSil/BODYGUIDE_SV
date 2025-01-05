package org.hepi.hepi_sv.nutrition.repository;

import java.util.Optional;
import java.util.UUID;

import org.hepi.hepi_sv.nutrition.entity.UsersNutritionProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersNutritionProfileRepository extends JpaRepository<UsersNutritionProfile, Long> {
    
    Optional<UsersNutritionProfile> findByUserId(UUID userId);

}
