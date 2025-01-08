package org.bodyguide_sv.web.repository;

import org.bodyguide_sv.web.entity.WebNutriAnalysisData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebNutriAnalysisDataRepository extends JpaRepository<WebNutriAnalysisData, Long> {
    
}
