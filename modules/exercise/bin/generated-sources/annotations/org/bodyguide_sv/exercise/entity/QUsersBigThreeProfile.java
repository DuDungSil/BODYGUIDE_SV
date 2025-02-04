package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsersBigThreeProfile is a Querydsl query type for UsersBigThreeProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersBigThreeProfile extends EntityPathBase<UsersBigThreeProfile> {

    private static final long serialVersionUID = 1458726356L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUsersBigThreeProfile usersBigThreeProfile = new QUsersBigThreeProfile("usersBigThreeProfile");

    public final org.bodyguide_sv.exercise.vo.QBigThreeProfile benchPress;

    public final org.bodyguide_sv.exercise.vo.QBigThreeProfile deadLift;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final org.bodyguide_sv.exercise.vo.QBigThreeProfile squat;

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersBigThreeProfile(String variable) {
        this(UsersBigThreeProfile.class, forVariable(variable), INITS);
    }

    public QUsersBigThreeProfile(Path<? extends UsersBigThreeProfile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUsersBigThreeProfile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUsersBigThreeProfile(PathMetadata metadata, PathInits inits) {
        this(UsersBigThreeProfile.class, metadata, inits);
    }

    public QUsersBigThreeProfile(Class<? extends UsersBigThreeProfile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.benchPress = inits.isInitialized("benchPress") ? new org.bodyguide_sv.exercise.vo.QBigThreeProfile(forProperty("benchPress")) : null;
        this.deadLift = inits.isInitialized("deadLift") ? new org.bodyguide_sv.exercise.vo.QBigThreeProfile(forProperty("deadLift")) : null;
        this.squat = inits.isInitialized("squat") ? new org.bodyguide_sv.exercise.vo.QBigThreeProfile(forProperty("squat")) : null;
    }

}

