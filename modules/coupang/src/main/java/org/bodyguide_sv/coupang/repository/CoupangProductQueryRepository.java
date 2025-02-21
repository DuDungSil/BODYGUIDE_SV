package org.bodyguide_sv.coupang.repository;

import java.util.List;

import org.bodyguide_sv.coupang.dto.CoupangProductDTO;
import org.bodyguide_sv.coupang.entity.QCoupangRecommendFood;
import org.bodyguide_sv.coupang.entity.QCoupangRecommendSupplements;
import org.bodyguide_sv.coupang.entity.QCoupangProduct;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CoupangProductQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<CoupangProductDTO> selectFoodsByNutrientTypeAndDietType(int nutrientTypeId, int dietTypeId) {
        QCoupangProduct coupangProduct = QCoupangProduct.coupangProduct;
        QCoupangRecommendFood coupangRecommendFood = QCoupangRecommendFood.coupangRecommendFood;

        return queryFactory
                .select(Projections.constructor(
                        CoupangProductDTO.class, // DTO 매핑
                        coupangProduct.productId,
                        coupangProduct.name,
                        coupangProduct.url,
                        coupangProduct.imgUrl,
                        coupangProduct.brand,
                        coupangProduct.price,
                        coupangProduct.salePrice,
                        coupangProduct.discount,
                        coupangProduct.rating,
                        coupangProduct.review,
                        coupangProduct.category,
                        coupangProduct.keyword,
                        coupangProduct.isRocketDelivery,
                        coupangProduct.isRocketFresh
                ))
                .from(coupangProduct)
                .innerJoin(coupangRecommendFood)
                .on(coupangProduct.productId.eq(coupangRecommendFood.productId))
                .where(
                        coupangRecommendFood.nutrientType.eq(nutrientTypeId),
                        coupangRecommendFood.dietType.eq(dietTypeId))
                .fetch();
    }

    public List<CoupangProductDTO> selectSupplementsByNutrientIds(List<Integer> nutrientIds) {
        QCoupangProduct coupangProduct = QCoupangProduct.coupangProduct;
        QCoupangRecommendSupplements coupangRecommendSupplements = QCoupangRecommendSupplements.coupangRecommendSupplements;

        return queryFactory
                .select(Projections.constructor(
                        CoupangProductDTO.class,
                        coupangProduct.productId,
                        coupangProduct.name,
                        coupangProduct.url,
                        coupangProduct.imgUrl,
                        coupangProduct.brand,
                        coupangProduct.price,
                        coupangProduct.salePrice,
                        coupangProduct.discount,
                        coupangProduct.rating,
                        coupangProduct.review,
                        coupangProduct.category,
                        coupangProduct.keyword,
                        coupangProduct.isRocketDelivery,
                        coupangProduct.isRocketFresh
                ))
                .from(coupangProduct)
                .innerJoin(coupangRecommendSupplements)
                .on(coupangProduct.productId.eq(coupangRecommendSupplements.productId))
                .where(coupangRecommendSupplements.relatedNutrientId.in(nutrientIds)) // IN 절 사용
                .fetch();
    }


}
