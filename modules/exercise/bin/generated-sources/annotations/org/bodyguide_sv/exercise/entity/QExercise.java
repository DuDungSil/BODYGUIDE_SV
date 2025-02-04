package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExercise is a Querydsl query type for Exercise
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExercise extends EntityPathBase<Exercise> {

    private static final long serialVersionUID = 563966217L;

    public static final QExercise exercise = new QExercise("exercise");

    public final NumberPath<Integer> exerId = createNumber("exerId", Integer.class);

    public final StringPath exerName = createString("exerName");

    public final StringPath exerNameKr = createString("exerNameKr");

    public final NumberPath<Integer> exerType = createNumber("exerType", Integer.class);

    public final NumberPath<Integer> muscleId = createNumber("muscleId", Integer.class);

    public final NumberPath<Integer> thresholdType = createNumber("thresholdType", Integer.class);

    public QExercise(String variable) {
        super(Exercise.class, forVariable(variable));
    }

    public QExercise(Path<? extends Exercise> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExercise(PathMetadata metadata) {
        super(Exercise.class, metadata);
    }

}

