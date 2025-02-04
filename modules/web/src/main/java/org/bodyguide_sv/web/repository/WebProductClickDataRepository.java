package org.bodyguide_sv.web.repository;

import org.bodyguide_sv.web.entity.WebProductClickData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebProductClickDataRepository extends JpaRepository<WebProductClickData, Long> {
    
}
