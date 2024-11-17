package org.hepi.hepi_sv.nutrition.repository;

import java.util.List;

import org.hepi.hepi_sv.nutrition.dto.NutrientProfile;
import org.hepi.hepi_sv.nutrition.entity.QLevelRecommendNutrition;
import org.hepi.hepi_sv.nutrition.entity.QNutritionInfo;
import org.hepi.hepi_sv.nutrition.entity.QPurposeRecommendNutrition;
import org.hepi.hepi_sv.nutrition.entity.QRecommendSource;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class NutritionQueryRepository {
    
    private final JPAQueryFactory queryFactory;

    // 급원 추천
    public List<String> selectSourceByNutrientAndDiet(String nutrientType, String dietType) {
        QRecommendSource recommendSource = QRecommendSource.recommendSource;

        return queryFactory
                .select(recommendSource.source)
                .from(recommendSource)
                .where(
                        recommendSource.nutrientType.eq(nutrientType),
                        recommendSource.dietType.eq(dietType))
                .fetch();
    }

    // 영양성분 추천 ( 운동 수준 )
    public List<NutrientProfile> selectNutrientProfilesByLevel(int level) {
        QNutritionInfo nutritionInfo = QNutritionInfo.nutritionInfo;
        QLevelRecommendNutrition levelRecommendNutrition = QLevelRecommendNutrition.levelRecommendNutrition;

        return queryFactory
                .select(Projections.bean(
                        NutrientProfile.class,
                        nutritionInfo.nutritionName.as("name"),
                        nutritionInfo.nutritionFunction.as("function"),
                        nutritionInfo.nutritionIntake.as("RDI"), // 권장 섭취량
                        nutritionInfo.nutritionSideEffect.as("sideEffect"),
                        nutritionInfo.nutritionPrecaution.as("precaution"),
                        nutritionInfo.nutritionTiming.as("timing"),
                        nutritionInfo.nutritionSummary.as("summary"),
                        levelRecommendNutrition.mention.as("mention")
                )
                )
                .from(nutritionInfo)
                .innerJoin(levelRecommendNutrition)
                .on(nutritionInfo.nutritionName.eq(levelRecommendNutrition.nutritionName))
                .where(levelRecommendNutrition.lvl.loe(level)) // LVL <= #{LEVEL}
                .fetch();
    }

    // 영양성분 추천 ( 운동 목적 )
    public List<NutrientProfile> selectNutrientProfilesByPurpose(String purpose) {
        QNutritionInfo nutritionInfo = QNutritionInfo.nutritionInfo;
        QPurposeRecommendNutrition purposeRecommendNutrition = QPurposeRecommendNutrition.purposeRecommendNutrition;

        return queryFactory
                .select(Projections.bean(
                        NutrientProfile.class,
                        nutritionInfo.nutritionName.as("name"),
                        nutritionInfo.nutritionFunction.as("function"),
                        nutritionInfo.nutritionIntake.as("RDI"), // 권장 섭취량
                        nutritionInfo.nutritionSideEffect.as("sideEffect"),
                        nutritionInfo.nutritionPrecaution.as("precaution"),
                        nutritionInfo.nutritionTiming.as("timing"),
                        nutritionInfo.nutritionSummary.as("summary")
                ))
                .from(nutritionInfo)
                .where(
                        nutritionInfo.nutritionName.in(
                                queryFactory
                                        .select(purposeRecommendNutrition.nutrition)
                                        .from(purposeRecommendNutrition)
                                        .where(purposeRecommendNutrition.purpose.eq(purpose))
                        )
                )
                .fetch();
    }
}
