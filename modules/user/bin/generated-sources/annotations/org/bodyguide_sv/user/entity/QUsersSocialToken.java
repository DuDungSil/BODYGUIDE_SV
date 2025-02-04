package org.bodyguide_sv.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersSocialToken is a Querydsl query type for UsersSocialToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersSocialToken extends EntityPathBase<UsersSocialToken> {

    private static final long serialVersionUID = -64184184L;

    public static final QUsersSocialToken usersSocialToken = new QUsersSocialToken("usersSocialToken");

    public final StringPath refreshToken = createString("refreshToken");

    public final NumberPath<Long> tokenId = createNumber("tokenId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersSocialToken(String variable) {
        super(UsersSocialToken.class, forVariable(variable));
    }

    public QUsersSocialToken(Path<? extends UsersSocialToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersSocialToken(PathMetadata metadata) {
        super(UsersSocialToken.class, metadata);
    }

}

