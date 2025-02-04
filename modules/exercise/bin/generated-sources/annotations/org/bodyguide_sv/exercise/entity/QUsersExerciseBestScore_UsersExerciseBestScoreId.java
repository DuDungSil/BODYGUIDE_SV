package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersExerciseBestScore_UsersExerciseBestScoreId is a Querydsl query type for UsersExerciseBestScoreId
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QUsersExerciseBestScore_UsersExerciseBestScoreId extends BeanPath<UsersExerciseBestScore.UsersExerciseBestScoreId> {

    private static final long serialVersionUID = -1643031688L;

    public static final QUsersExerciseBestScore_UsersExerciseBestScoreId usersExerciseBestScoreId = new QUsersExerciseBestScore_UsersExerciseBestScoreId("usersExerciseBestScoreId");

    public final NumberPath<Integer> exerciseId = createNumber("exerciseId", Integer.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersExerciseBestScore_UsersExerciseBestScoreId(String variable) {
        super(UsersExerciseBestScore.UsersExerciseBestScoreId.class, forVariable(variable));
    }

    public QUsersExerciseBestScore_UsersExerciseBestScoreId(Path<? extends UsersExerciseBestScore.UsersExerciseBestScoreId> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersExerciseBestScore_UsersExerciseBestScoreId(PathMetadata metadata) {
        super(UsersExerciseBestScore.UsersExerciseBestScoreId.class, metadata);
    }

}

