package org.hepi.hepi_sv.nutrition.repository;

import org.hepi.hepi_sv.nutrition.entity.UsersNutritionProfileHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersNutritionProfileHistoryRepository extends JpaRepository<UsersNutritionProfileHistory, Long> {
    
}
