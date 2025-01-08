package org.bodyguide_sv.web.repository;

import org.bodyguide_sv.web.entity.WebExerInputData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebExerInputDataRepository extends JpaRepository<WebExerInputData, Long> {
    
}
