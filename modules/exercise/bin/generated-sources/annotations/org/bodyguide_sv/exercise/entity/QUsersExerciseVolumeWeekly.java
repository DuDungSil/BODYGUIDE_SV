package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersExerciseVolumeWeekly is a Querydsl query type for UsersExerciseVolumeWeekly
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersExerciseVolumeWeekly extends EntityPathBase<UsersExerciseVolumeWeekly> {

    private static final long serialVersionUID = -915107510L;

    public static final QUsersExerciseVolumeWeekly usersExerciseVolumeWeekly = new QUsersExerciseVolumeWeekly("usersExerciseVolumeWeekly");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public final NumberPath<Double> volume = createNumber("volume", Double.class);

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QUsersExerciseVolumeWeekly(String variable) {
        super(UsersExerciseVolumeWeekly.class, forVariable(variable));
    }

    public QUsersExerciseVolumeWeekly(Path<? extends UsersExerciseVolumeWeekly> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersExerciseVolumeWeekly(PathMetadata metadata) {
        super(UsersExerciseVolumeWeekly.class, metadata);
    }

}

