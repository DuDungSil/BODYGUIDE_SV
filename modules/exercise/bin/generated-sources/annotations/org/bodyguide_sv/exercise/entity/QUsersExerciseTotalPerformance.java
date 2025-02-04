package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersExerciseTotalPerformance is a Querydsl query type for UsersExerciseTotalPerformance
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersExerciseTotalPerformance extends EntityPathBase<UsersExerciseTotalPerformance> {

    private static final long serialVersionUID = -2102770725L;

    public static final QUsersExerciseTotalPerformance usersExerciseTotalPerformance = new QUsersExerciseTotalPerformance("usersExerciseTotalPerformance");

    public final NumberPath<Double> bigThree = createNumber("bigThree", Double.class);

    public final DateTimePath<java.time.LocalDateTime> bigThreeUpdatedAt = createDateTime("bigThreeUpdatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> exerciseLevel = createNumber("exerciseLevel", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> levelUpdatedAt = createDateTime("levelUpdatedAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> scoreUpdatedAt = createDateTime("scoreUpdatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Double> totalScore = createNumber("totalScore", Double.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersExerciseTotalPerformance(String variable) {
        super(UsersExerciseTotalPerformance.class, forVariable(variable));
    }

    public QUsersExerciseTotalPerformance(Path<? extends UsersExerciseTotalPerformance> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersExerciseTotalPerformance(PathMetadata metadata) {
        super(UsersExerciseTotalPerformance.class, metadata);
    }

}

