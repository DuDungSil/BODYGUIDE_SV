package org.bodyguide_sv.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersProfile is a Querydsl query type for UsersProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersProfile extends EntityPathBase<UsersProfile> {

    private static final long serialVersionUID = 2114533797L;

    public static final QUsersProfile usersProfile = new QUsersProfile("usersProfile");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final StringPath gender = createString("gender");

    public final NumberPath<Double> height = createNumber("height", Double.class);

    public final StringPath introText = createString("introText");

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Long> profileId = createNumber("profileId", Long.class);

    public final StringPath profileImg = createString("profileImg");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QUsersProfile(String variable) {
        super(UsersProfile.class, forVariable(variable));
    }

    public QUsersProfile(Path<? extends UsersProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersProfile(PathMetadata metadata) {
        super(UsersProfile.class, metadata);
    }

}

