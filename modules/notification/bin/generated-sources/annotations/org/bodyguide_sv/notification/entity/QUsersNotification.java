package org.bodyguide_sv.notification.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersNotification is a Querydsl query type for UsersNotification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersNotification extends EntityPathBase<UsersNotification> {

    private static final long serialVersionUID = -2101503665L;

    public static final QUsersNotification usersNotification = new QUsersNotification("usersNotification");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRead = createBoolean("isRead");

    public final DateTimePath<java.time.LocalDateTime> readAt = createDateTime("readAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> receiverId = createComparable("receiverId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> senderId = createComparable("senderId", java.util.UUID.class);

    public final EnumPath<org.bodyguide_sv.notification.enums.NotificationType> type = createEnum("type", org.bodyguide_sv.notification.enums.NotificationType.class);

    public QUsersNotification(String variable) {
        super(UsersNotification.class, forVariable(variable));
    }

    public QUsersNotification(Path<? extends UsersNotification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersNotification(PathMetadata metadata) {
        super(UsersNotification.class, metadata);
    }

}

