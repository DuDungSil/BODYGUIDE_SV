package org.bodyguide_sv.web.repository;

import org.bodyguide_sv.web.entity.WebNutriInputData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebNutriInputDataRepository extends JpaRepository<WebNutriInputData, Long> {
    
}
