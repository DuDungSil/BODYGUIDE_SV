package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMuscle is a Querydsl query type for Muscle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMuscle extends EntityPathBase<Muscle> {

    private static final long serialVersionUID = 1773619426L;

    public static final QMuscle muscle = new QMuscle("muscle");

    public final NumberPath<Integer> muscleGroupId = createNumber("muscleGroupId", Integer.class);

    public final NumberPath<Integer> muscleId = createNumber("muscleId", Integer.class);

    public final StringPath name = createString("name");

    public final StringPath nameKr = createString("nameKr");

    public QMuscle(String variable) {
        super(Muscle.class, forVariable(variable));
    }

    public QMuscle(Path<? extends Muscle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMuscle(PathMetadata metadata) {
        super(Muscle.class, metadata);
    }

}

