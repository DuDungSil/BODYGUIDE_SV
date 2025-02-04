package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersExerciseSetHistory is a Querydsl query type for UsersExerciseSetHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersExerciseSetHistory extends EntityPathBase<UsersExerciseSetHistory> {

    private static final long serialVersionUID = -1336680799L;

    public static final QUsersExerciseSetHistory usersExerciseSetHistory = new QUsersExerciseSetHistory("usersExerciseSetHistory");

    public final DateTimePath<java.time.LocalDateTime> exerciseDate = createDateTime("exerciseDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> exerciseId = createNumber("exerciseId", Integer.class);

    public final NumberPath<Integer> groupId = createNumber("groupId", Integer.class);

    public final NumberPath<Long> historyId = createNumber("historyId", Long.class);

    public final NumberPath<Integer> reps = createNumber("reps", Integer.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final NumberPath<Integer> set = createNumber("set", Integer.class);

    public final NumberPath<Double> strength = createNumber("strength", Double.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QUsersExerciseSetHistory(String variable) {
        super(UsersExerciseSetHistory.class, forVariable(variable));
    }

    public QUsersExerciseSetHistory(Path<? extends UsersExerciseSetHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersExerciseSetHistory(PathMetadata metadata) {
        super(UsersExerciseSetHistory.class, metadata);
    }

}

