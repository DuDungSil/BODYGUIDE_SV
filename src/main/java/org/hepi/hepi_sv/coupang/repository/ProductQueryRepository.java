package org.hepi.hepi_sv.coupang.repository;

import java.util.List;

import org.hepi.hepi_sv.coupang.dto.ShopProductDTO;
import org.hepi.hepi_sv.coupang.entity.QRecommendFood;
import org.hepi.hepi_sv.coupang.entity.QRecommendSupplements;
import org.hepi.hepi_sv.coupang.entity.QShopProduct;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ProductQueryRepository {
    
    private final JPAQueryFactory queryFactory;
    
    public List<ShopProductDTO> selectFoodsByNutrientTypeAndDietType(int nutrientTypeId, int dietTypeId) {
        QShopProduct shopProduct = QShopProduct.shopProduct;
        QRecommendFood recommendFood = QRecommendFood.recommendFood;

        return queryFactory
                .select(Projections.constructor(
                        ShopProductDTO.class, // DTO 매핑
                        shopProduct.productId,
                        shopProduct.name,
                        shopProduct.url,
                        shopProduct.imgUrl,
                        shopProduct.brand,
                        shopProduct.price,
                        shopProduct.salePrice,
                        shopProduct.discount,
                        shopProduct.rating,
                        shopProduct.review,
                        shopProduct.category,
                        shopProduct.keyword,
                        shopProduct.isRocketDelivery,
                        shopProduct.isRocketFresh
                ))
                .from(shopProduct)
                .innerJoin(recommendFood)
                .on(shopProduct.productId.eq(recommendFood.productId))
                .where(
                        recommendFood.nutrientType.eq(nutrientTypeId),
                        recommendFood.dietType.eq(dietTypeId))
                .fetch();
    }

    public List<ShopProductDTO> selectSupplementsByNutrientName(int nutrientId) {
        QShopProduct shopProduct = QShopProduct.shopProduct;
        QRecommendSupplements recommendSupplements = QRecommendSupplements.recommendSupplements;

        return queryFactory
                .select(Projections.constructor(
                        ShopProductDTO.class, // DTO 매핑
                        shopProduct.productId,
                        shopProduct.name,
                        shopProduct.url,
                        shopProduct.imgUrl,
                        shopProduct.brand,
                        shopProduct.price,
                        shopProduct.salePrice,
                        shopProduct.discount,
                        shopProduct.rating,
                        shopProduct.review,
                        shopProduct.category,
                        shopProduct.keyword,
                        shopProduct.isRocketDelivery,
                        shopProduct.isRocketFresh
                ))
                .from(shopProduct)
                .innerJoin(recommendSupplements)
                .on(shopProduct.productId.eq(recommendSupplements.productId))
                .where(
                    recommendSupplements.relatedNutrientId.eq(nutrientId)
                )
                .fetch();
    }

}