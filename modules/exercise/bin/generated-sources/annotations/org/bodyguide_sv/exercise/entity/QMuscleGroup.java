package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMuscleGroup is a Querydsl query type for MuscleGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMuscleGroup extends EntityPathBase<MuscleGroup> {

    private static final long serialVersionUID = 1935342269L;

    public static final QMuscleGroup muscleGroup = new QMuscleGroup("muscleGroup");

    public final NumberPath<Integer> groupId = createNumber("groupId", Integer.class);

    public final StringPath groupName = createString("groupName");

    public final StringPath strength = createString("strength");

    public QMuscleGroup(String variable) {
        super(MuscleGroup.class, forVariable(variable));
    }

    public QMuscleGroup(Path<? extends MuscleGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMuscleGroup(PathMetadata metadata) {
        super(MuscleGroup.class, metadata);
    }

}

