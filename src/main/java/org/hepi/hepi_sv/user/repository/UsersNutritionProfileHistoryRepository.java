package org.hepi.hepi_sv.user.repository;

import org.hepi.hepi_sv.user.entity.UsersNutritionProfileHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersNutritionProfileHistoryRepository extends JpaRepository<UsersNutritionProfileHistory, Long> {
    
}
