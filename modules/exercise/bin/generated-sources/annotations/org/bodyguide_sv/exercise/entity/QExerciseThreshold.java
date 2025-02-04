package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExerciseThreshold is a Querydsl query type for ExerciseThreshold
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExerciseThreshold extends EntityPathBase<ExerciseThreshold> {

    private static final long serialVersionUID = 2077928802L;

    public static final QExerciseThreshold exerciseThreshold = new QExerciseThreshold("exerciseThreshold");

    public final NumberPath<Integer> exerId = createNumber("exerId", Integer.class);

    public final StringPath gender = createString("gender");

    public final NumberPath<Integer> rank = createNumber("rank", Integer.class);

    public final NumberPath<Double> threshold = createNumber("threshold", Double.class);

    public final NumberPath<Integer> thresholdId = createNumber("thresholdId", Integer.class);

    public QExerciseThreshold(String variable) {
        super(ExerciseThreshold.class, forVariable(variable));
    }

    public QExerciseThreshold(Path<? extends ExerciseThreshold> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExerciseThreshold(PathMetadata metadata) {
        super(ExerciseThreshold.class, metadata);
    }

}

