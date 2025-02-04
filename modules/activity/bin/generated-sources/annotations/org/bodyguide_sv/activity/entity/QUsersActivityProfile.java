package org.bodyguide_sv.activity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersActivityProfile is a Querydsl query type for UsersActivityProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersActivityProfile extends EntityPathBase<UsersActivityProfile> {

    private static final long serialVersionUID = 161906394L;

    public static final QUsersActivityProfile usersActivityProfile = new QUsersActivityProfile("usersActivityProfile");

    public final NumberPath<Integer> dietCount = createNumber("dietCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> dietUpdatedAt = createDateTime("dietUpdatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> exerciseCount = createNumber("exerciseCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> exerciseUpdatedAt = createDateTime("exerciseUpdatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public final NumberPath<Integer> weightCount = createNumber("weightCount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> weightUpdatedAt = createDateTime("weightUpdatedAt", java.time.LocalDateTime.class);

    public QUsersActivityProfile(String variable) {
        super(UsersActivityProfile.class, forVariable(variable));
    }

    public QUsersActivityProfile(Path<? extends UsersActivityProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersActivityProfile(PathMetadata metadata) {
        super(UsersActivityProfile.class, metadata);
    }

}

