package org.hepi.hepi_sv.product.repository;

import org.hepi.hepi_sv.product.entity.ShopProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopProductRepository extends JpaRepository<ShopProduct, Integer>{
    
}
