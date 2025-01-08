package org.hepi.hepi_sv.coupang.repository;

import org.hepi.hepi_sv.coupang.entity.CoupangProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoupangProductRepository extends JpaRepository<CoupangProduct, Integer>{
    
}
