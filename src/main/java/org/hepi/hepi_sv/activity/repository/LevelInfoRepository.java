package org.hepi.hepi_sv.activity.repository;

import org.hepi.hepi_sv.activity.entity.LevelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LevelInfoRepository extends JpaRepository<LevelInfo, Integer> {
    
}
