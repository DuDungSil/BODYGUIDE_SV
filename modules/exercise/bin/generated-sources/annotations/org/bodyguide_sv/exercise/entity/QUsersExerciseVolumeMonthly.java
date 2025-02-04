package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersExerciseVolumeMonthly is a Querydsl query type for UsersExerciseVolumeMonthly
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersExerciseVolumeMonthly extends EntityPathBase<UsersExerciseVolumeMonthly> {

    private static final long serialVersionUID = 1706203236L;

    public static final QUsersExerciseVolumeMonthly usersExerciseVolumeMonthly = new QUsersExerciseVolumeMonthly("usersExerciseVolumeMonthly");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public final NumberPath<Double> volume = createNumber("volume", Double.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QUsersExerciseVolumeMonthly(String variable) {
        super(UsersExerciseVolumeMonthly.class, forVariable(variable));
    }

    public QUsersExerciseVolumeMonthly(Path<? extends UsersExerciseVolumeMonthly> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersExerciseVolumeMonthly(PathMetadata metadata) {
        super(UsersExerciseVolumeMonthly.class, metadata);
    }

}

