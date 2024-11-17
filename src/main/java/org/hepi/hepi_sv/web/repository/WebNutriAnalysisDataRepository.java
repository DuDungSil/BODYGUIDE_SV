package org.hepi.hepi_sv.web.repository;

import org.hepi.hepi_sv.web.entity.WebNutriAnalysisData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebNutriAnalysisDataRepository extends JpaRepository<WebNutriAnalysisData, Long> {
    
}
