package org.bodyguide_sv.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersMeta is a Querydsl query type for UsersMeta
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersMeta extends EntityPathBase<UsersMeta> {

    private static final long serialVersionUID = -49625207L;

    public static final QUsersMeta usersMeta = new QUsersMeta("usersMeta");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastLoginAt = createDateTime("lastLoginAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> metaId = createNumber("metaId", Long.class);

    public final StringPath source = createString("source");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersMeta(String variable) {
        super(UsersMeta.class, forVariable(variable));
    }

    public QUsersMeta(Path<? extends UsersMeta> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersMeta(PathMetadata metadata) {
        super(UsersMeta.class, metadata);
    }

}

