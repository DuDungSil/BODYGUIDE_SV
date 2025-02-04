package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExercisePurpose is a Querydsl query type for ExercisePurpose
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExercisePurpose extends EntityPathBase<ExercisePurpose> {

    private static final long serialVersionUID = -1517250603L;

    public static final QExercisePurpose exercisePurpose = new QExercisePurpose("exercisePurpose");

    public final StringPath purpose = createString("purpose");

    public final NumberPath<Integer> purposeId = createNumber("purposeId", Integer.class);

    public QExercisePurpose(String variable) {
        super(ExercisePurpose.class, forVariable(variable));
    }

    public QExercisePurpose(Path<? extends ExercisePurpose> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExercisePurpose(PathMetadata metadata) {
        super(ExercisePurpose.class, metadata);
    }

}

