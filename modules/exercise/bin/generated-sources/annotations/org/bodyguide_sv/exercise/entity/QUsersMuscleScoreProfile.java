package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsersMuscleScoreProfile is a Querydsl query type for UsersMuscleScoreProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersMuscleScoreProfile extends EntityPathBase<UsersMuscleScoreProfile> {

    private static final long serialVersionUID = -345690209L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsersMuscleScoreProfile usersMuscleScoreProfile = new QUsersMuscleScoreProfile("usersMuscleScoreProfile");

    public final org.bodyguide_sv.exercise.vo.QMuscleScoreProfile arm;

    public final org.bodyguide_sv.exercise.vo.QMuscleScoreProfile back;

    public final org.bodyguide_sv.exercise.vo.QMuscleScoreProfile chest;

    public final org.bodyguide_sv.exercise.vo.QMuscleScoreProfile core;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final org.bodyguide_sv.exercise.vo.QMuscleScoreProfile lowerBody;

    public final org.bodyguide_sv.exercise.vo.QMuscleScoreProfile shoulder;

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersMuscleScoreProfile(String variable) {
        this(UsersMuscleScoreProfile.class, forVariable(variable), INITS);
    }

    public QUsersMuscleScoreProfile(Path<? extends UsersMuscleScoreProfile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsersMuscleScoreProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsersMuscleScoreProfile(PathMetadata metadata, PathInits inits) {
        this(UsersMuscleScoreProfile.class, metadata, inits);
    }

    public QUsersMuscleScoreProfile(Class<? extends UsersMuscleScoreProfile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.arm = inits.isInitialized("arm") ? new org.bodyguide_sv.exercise.vo.QMuscleScoreProfile(forProperty("arm")) : null;
        this.back = inits.isInitialized("back") ? new org.bodyguide_sv.exercise.vo.QMuscleScoreProfile(forProperty("back")) : null;
        this.chest = inits.isInitialized("chest") ? new org.bodyguide_sv.exercise.vo.QMuscleScoreProfile(forProperty("chest")) : null;
        this.core = inits.isInitialized("core") ? new org.bodyguide_sv.exercise.vo.QMuscleScoreProfile(forProperty("core")) : null;
        this.lowerBody = inits.isInitialized("lowerBody") ? new org.bodyguide_sv.exercise.vo.QMuscleScoreProfile(forProperty("lowerBody")) : null;
        this.shoulder = inits.isInitialized("shoulder") ? new org.bodyguide_sv.exercise.vo.QMuscleScoreProfile(forProperty("shoulder")) : null;
    }

}

