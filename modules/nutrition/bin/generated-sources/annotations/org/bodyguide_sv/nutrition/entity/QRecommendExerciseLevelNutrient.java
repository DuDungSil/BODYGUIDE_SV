package org.bodyguide_sv.nutrition.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecommendExerciseLevelNutrient is a Querydsl query type for RecommendExerciseLevelNutrient
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendExerciseLevelNutrient extends EntityPathBase<RecommendExerciseLevelNutrient> {

    private static final long serialVersionUID = -2077998608L;

    public static final QRecommendExerciseLevelNutrient recommendExerciseLevelNutrient = new QRecommendExerciseLevelNutrient("recommendExerciseLevelNutrient");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> lvl = createNumber("lvl", Integer.class);

    public final StringPath mention = createString("mention");

    public final NumberPath<Integer> nutrientId = createNumber("nutrientId", Integer.class);

    public QRecommendExerciseLevelNutrient(String variable) {
        super(RecommendExerciseLevelNutrient.class, forVariable(variable));
    }

    public QRecommendExerciseLevelNutrient(Path<? extends RecommendExerciseLevelNutrient> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecommendExerciseLevelNutrient(PathMetadata metadata) {
        super(RecommendExerciseLevelNutrient.class, metadata);
    }

}

