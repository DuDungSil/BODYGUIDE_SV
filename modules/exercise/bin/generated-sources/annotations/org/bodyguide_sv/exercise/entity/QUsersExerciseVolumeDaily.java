package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersExerciseVolumeDaily is a Querydsl query type for UsersExerciseVolumeDaily
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersExerciseVolumeDaily extends EntityPathBase<UsersExerciseVolumeDaily> {

    private static final long serialVersionUID = -47181776L;

    public static final QUsersExerciseVolumeDaily usersExerciseVolumeDaily = new QUsersExerciseVolumeDaily("usersExerciseVolumeDaily");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public final NumberPath<Double> volume = createNumber("volume", Double.class);

    public QUsersExerciseVolumeDaily(String variable) {
        super(UsersExerciseVolumeDaily.class, forVariable(variable));
    }

    public QUsersExerciseVolumeDaily(Path<? extends UsersExerciseVolumeDaily> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersExerciseVolumeDaily(PathMetadata metadata) {
        super(UsersExerciseVolumeDaily.class, metadata);
    }

}

