package org.hepi.hepi_sv.nutrition.repository;

import org.hepi.hepi_sv.nutrition.entity.DietType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  DietTypeRepository extends JpaRepository<DietType, Integer> {
    
}
