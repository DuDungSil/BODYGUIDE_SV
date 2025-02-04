package org.bodyguide_sv.weight.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersWeightProfile is a Querydsl query type for UsersWeightProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersWeightProfile extends EntityPathBase<UsersWeightProfile> {

    private static final long serialVersionUID = -1579981702L;

    public static final QUsersWeightProfile usersWeightProfile = new QUsersWeightProfile("usersWeightProfile");

    public final NumberPath<Long> profileId = createNumber("profileId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> recordDate = createDateTime("recordDate", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QUsersWeightProfile(String variable) {
        super(UsersWeightProfile.class, forVariable(variable));
    }

    public QUsersWeightProfile(Path<? extends UsersWeightProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersWeightProfile(PathMetadata metadata) {
        super(UsersWeightProfile.class, metadata);
    }

}

