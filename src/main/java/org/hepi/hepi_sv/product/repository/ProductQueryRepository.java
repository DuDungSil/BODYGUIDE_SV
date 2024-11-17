package org.hepi.hepi_sv.product.repository;

import java.util.List;

import org.hepi.hepi_sv.product.entity.QRecommendFood;
import org.hepi.hepi_sv.product.entity.QRecommendSupplements;
import org.hepi.hepi_sv.product.entity.QShopProduct;
import org.hepi.hepi_sv.product.entity.ShopProduct;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {
    
    private final JPAQueryFactory queryFactory;
    
    public List<ShopProduct> selectFoodsByNutrientTypeAndDietType(String nutrientType, String dietType) {
        QShopProduct shopProduct = QShopProduct.shopProduct;
        QRecommendFood recommendFood = QRecommendFood.recommendFood;

        return queryFactory
                .select(shopProduct)
                .from(shopProduct)
                .innerJoin(recommendFood)
                .on(shopProduct.product_id.eq(recommendFood.productId))
                .where(
                        recommendFood.nutrientType.eq(nutrientType),
                        recommendFood.dietType.eq(dietType))
                .fetch();
    }

    public List<ShopProduct> selectSupplementsByNutrientName(String nutrientName) {
        QShopProduct shopProduct = QShopProduct.shopProduct;
        QRecommendSupplements recommendSupplements = QRecommendSupplements.recommendSupplements;

        return queryFactory
            .select(shopProduct)
            .from(shopProduct)
            .innerJoin(recommendSupplements)
            .on(shopProduct.product_id.eq(recommendSupplements.productId))
            .where(
                recommendSupplements.relatedNutrient.eq(nutrientName)
            )
            .fetch();
    }

}