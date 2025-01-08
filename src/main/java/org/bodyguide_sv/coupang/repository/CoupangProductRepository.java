package org.bodyguide_sv.coupang.repository;

import org.bodyguide_sv.coupang.entity.CoupangProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoupangProductRepository extends JpaRepository<CoupangProduct, Integer>{
    
}
