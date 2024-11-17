package org.hepi.hepi_sv.web.repository;

import org.hepi.hepi_sv.web.entity.WebProductClickData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebProductClickDataRepository extends JpaRepository<WebProductClickData, Long> {
    
}
