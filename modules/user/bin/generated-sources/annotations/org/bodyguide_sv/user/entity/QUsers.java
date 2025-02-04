package org.bodyguide_sv.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = -633041052L;

    public static final QUsers users = new QUsers("users");

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final BooleanPath isDelete = createBoolean("isDelete");

    public final StringPath name = createString("name");

    public final EnumPath<org.bodyguide_sv.common.enums.SocialProvider> provider = createEnum("provider", org.bodyguide_sv.common.enums.SocialProvider.class);

    public final StringPath providerId = createString("providerId");

    public final EnumPath<org.bodyguide_sv.common.enums.Role> role = createEnum("role", org.bodyguide_sv.common.enums.Role.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

