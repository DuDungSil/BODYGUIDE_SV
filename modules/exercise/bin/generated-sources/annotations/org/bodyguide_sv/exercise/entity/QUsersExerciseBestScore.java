package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsersExerciseBestScore is a Querydsl query type for UsersExerciseBestScore
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersExerciseBestScore extends EntityPathBase<UsersExerciseBestScore> {

    private static final long serialVersionUID = 277479519L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsersExerciseBestScore usersExerciseBestScore = new QUsersExerciseBestScore("usersExerciseBestScore");

    public final QUsersExerciseBestScore_UsersExerciseBestScoreId id;

    public final NumberPath<Integer> reps = createNumber("reps", Integer.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final NumberPath<Integer> scoreReps = createNumber("scoreReps", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> scoreUpdatedAt = createDateTime("scoreUpdatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Double> scoreWeight = createNumber("scoreWeight", Double.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public final DateTimePath<java.time.LocalDateTime> weightUpdatedAt = createDateTime("weightUpdatedAt", java.time.LocalDateTime.class);

    public QUsersExerciseBestScore(String variable) {
        this(UsersExerciseBestScore.class, forVariable(variable), INITS);
    }

    public QUsersExerciseBestScore(Path<? extends UsersExerciseBestScore> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsersExerciseBestScore(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsersExerciseBestScore(PathMetadata metadata, PathInits inits) {
        this(UsersExerciseBestScore.class, metadata, inits);
    }

    public QUsersExerciseBestScore(Class<? extends UsersExerciseBestScore> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.id = inits.isInitialized("id") ? new QUsersExerciseBestScore_UsersExerciseBestScoreId(forProperty("id")) : null;
    }

}

