package org.bodyguide_sv.recommend.repository.custom.impl;

import java.util.List;

import org.bodyguide_sv.exercise.entity.QExercisePurpose;
import org.bodyguide_sv.nutrition.dto.NutrientProfile;
import org.bodyguide_sv.nutrition.entity.QNutrientInfo;
import org.bodyguide_sv.recommend.entity.QRecommendExerciseLevelNutrient;
import org.bodyguide_sv.recommend.entity.QRecommendExercisePurposeNutrient;
import org.bodyguide_sv.recommend.repository.custom.RecommendNutrientCustomRepository;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class RecommendNutrientCustomRepositoryImpl implements RecommendNutrientCustomRepository {

    private final JPAQueryFactory queryFactory;

    // 영양성분 추천 ( 운동 수준 )
    @Override
    public List<NutrientProfile> selectNutrientProfilesByLevel(int level) {
        QNutrientInfo nutrientInfo = QNutrientInfo.nutrientInfo;
        QRecommendExerciseLevelNutrient recommendExerciseLevelNutrient = QRecommendExerciseLevelNutrient.recommendExerciseLevelNutrient;

        return queryFactory
                .select(Projections.bean(
                        NutrientProfile.class,
                        nutrientInfo.id.as("id"),
                        nutrientInfo.name.as("name"),
                        nutrientInfo.function.as("function"),
                        nutrientInfo.intake.as("RDI"), // 권장 섭취량
                        nutrientInfo.sideEffect.as("sideEffect"),
                        nutrientInfo.precaution.as("precaution"),
                        nutrientInfo.timing.as("timing"),
                        nutrientInfo.summary.as("summary"),
                        recommendExerciseLevelNutrient.mention.as("mention")
                )
                )
                .from(nutrientInfo)
                .innerJoin(recommendExerciseLevelNutrient)
                .on(nutrientInfo.id.eq(recommendExerciseLevelNutrient.nutrientId))
                .where(recommendExerciseLevelNutrient.lvl.loe(level)) // LVL <= #{LEVEL}
                .fetch();
    }

    // 영양성분 추천 ( 운동 목적 )
    @Override
    public List<NutrientProfile> selectNutrientProfilesByPurposeName(String purpose) {

        QNutrientInfo nutrientInfo = QNutrientInfo.nutrientInfo;
        QExercisePurpose exercisePurpose = QExercisePurpose.exercisePurpose;
        QRecommendExercisePurposeNutrient recommendExercisePurposeNutrient = QRecommendExercisePurposeNutrient.recommendExercisePurposeNutrient;

        return queryFactory
                .select(Projections.bean(
                        NutrientProfile.class,
                        nutrientInfo.id.as("id"),
                        nutrientInfo.name.as("name"),
                        nutrientInfo.function.as("function"),
                        nutrientInfo.intake.as("RDI"), // 권장 섭취량
                        nutrientInfo.sideEffect.as("sideEffect"),
                        nutrientInfo.precaution.as("precaution"),
                        nutrientInfo.timing.as("timing"),
                        nutrientInfo.summary.as("summary")))
                .from(nutrientInfo)
                .where(
                        nutrientInfo.id.in(
                                queryFactory
                                        .select(recommendExercisePurposeNutrient.nutrientId)
                                        .from(recommendExercisePurposeNutrient)
                                        .innerJoin(exercisePurpose)
                                        .on(recommendExercisePurposeNutrient.purposeId
                                                .eq(exercisePurpose.purposeId))
                                        .where(exercisePurpose.purpose
                                                .eq(purpose))))
                .fetch();
    }

    // 영양성분 추천 ( 운동 목적 )
    @Override
    public List<NutrientProfile> selectNutrientProfilesByPurposeId(int purposeId) {
        QNutrientInfo nutrientInfo = QNutrientInfo.nutrientInfo;
        QExercisePurpose exercisePurpose = QExercisePurpose.exercisePurpose;
        QRecommendExercisePurposeNutrient recommendExercisePurposeNutrient = QRecommendExercisePurposeNutrient.recommendExercisePurposeNutrient;

        return queryFactory
                .select(Projections.bean(
                        NutrientProfile.class,
                        nutrientInfo.id.as("id"),
                        nutrientInfo.name.as("name"),
                        nutrientInfo.function.as("function"),
                        nutrientInfo.intake.as("RDI"), // 권장 섭취량
                        nutrientInfo.sideEffect.as("sideEffect"),
                        nutrientInfo.precaution.as("precaution"),
                        nutrientInfo.timing.as("timing"),
                        nutrientInfo.summary.as("summary")
                ))
                .from(nutrientInfo)
                .where(
                        nutrientInfo.id.in(
                                queryFactory
                                        .select(recommendExercisePurposeNutrient.nutrientId)
                                        .from(recommendExercisePurposeNutrient)
                                        .innerJoin(exercisePurpose)
                                        .on(recommendExercisePurposeNutrient.purposeId.eq(exercisePurpose.purposeId))
                                        .where(exercisePurpose.purposeId.eq(purposeId))
                        )
                )
                .fetch();
    }

}
