package org.bodyguide_sv.recommend.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecommendExercisePurposeNutrient is a Querydsl query type for RecommendExercisePurposeNutrient
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendExercisePurposeNutrient extends EntityPathBase<RecommendExercisePurposeNutrient> {

    private static final long serialVersionUID = -1625750162L;

    public static final QRecommendExercisePurposeNutrient recommendExercisePurposeNutrient = new QRecommendExercisePurposeNutrient("recommendExercisePurposeNutrient");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> nutrientId = createNumber("nutrientId", Integer.class);

    public final NumberPath<Integer> purposeId = createNumber("purposeId", Integer.class);

    public QRecommendExercisePurposeNutrient(String variable) {
        super(RecommendExercisePurposeNutrient.class, forVariable(variable));
    }

    public QRecommendExercisePurposeNutrient(Path<? extends RecommendExercisePurposeNutrient> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecommendExercisePurposeNutrient(PathMetadata metadata) {
        super(RecommendExercisePurposeNutrient.class, metadata);
    }

}

