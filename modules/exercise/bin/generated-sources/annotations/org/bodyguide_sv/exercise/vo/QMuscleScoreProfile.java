package org.bodyguide_sv.exercise.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMuscleScoreProfile is a Querydsl query type for MuscleScoreProfile
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QMuscleScoreProfile extends BeanPath<MuscleScoreProfile> {

    private static final long serialVersionUID = -1246904029L;

    public static final QMuscleScoreProfile muscleScoreProfile = new QMuscleScoreProfile("muscleScoreProfile");

    public final NumberPath<Integer> exerciseId = createNumber("exerciseId", Integer.class);

    public final NumberPath<Integer> reps = createNumber("reps", Integer.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QMuscleScoreProfile(String variable) {
        super(MuscleScoreProfile.class, forVariable(variable));
    }

    public QMuscleScoreProfile(Path<? extends MuscleScoreProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMuscleScoreProfile(PathMetadata metadata) {
        super(MuscleScoreProfile.class, metadata);
    }

}

