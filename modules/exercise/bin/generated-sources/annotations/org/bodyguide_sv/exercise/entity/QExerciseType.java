package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExerciseType is a Querydsl query type for ExerciseType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExerciseType extends EntityPathBase<ExerciseType> {

    private static final long serialVersionUID = 1143195619L;

    public static final QExerciseType exerciseType = new QExerciseType("exerciseType");

    public final StringPath type = createString("type");

    public final NumberPath<Long> typeId = createNumber("typeId", Long.class);

    public final StringPath typeKr = createString("typeKr");

    public QExerciseType(String variable) {
        super(ExerciseType.class, forVariable(variable));
    }

    public QExerciseType(Path<? extends ExerciseType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExerciseType(PathMetadata metadata) {
        super(ExerciseType.class, metadata);
    }

}

