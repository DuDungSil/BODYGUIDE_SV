package org.bodyguide_sv.activity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersExpProfile is a Querydsl query type for UsersExpProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersExpProfile extends EntityPathBase<UsersExpProfile> {

    private static final long serialVersionUID = 746247564L;

    public static final QUsersExpProfile usersExpProfile = new QUsersExpProfile("usersExpProfile");

    public final NumberPath<Integer> currentLevel = createNumber("currentLevel", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> totalExp = createNumber("totalExp", Integer.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersExpProfile(String variable) {
        super(UsersExpProfile.class, forVariable(variable));
    }

    public QUsersExpProfile(Path<? extends UsersExpProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersExpProfile(PathMetadata metadata) {
        super(UsersExpProfile.class, metadata);
    }

}

