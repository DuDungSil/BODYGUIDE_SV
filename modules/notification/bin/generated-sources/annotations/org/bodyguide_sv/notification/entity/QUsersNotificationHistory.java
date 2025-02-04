package org.bodyguide_sv.notification.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersNotificationHistory is a Querydsl query type for UsersNotificationHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersNotificationHistory extends EntityPathBase<UsersNotificationHistory> {

    private static final long serialVersionUID = -1687577531L;

    public static final QUsersNotificationHistory usersNotificationHistory = new QUsersNotificationHistory("usersNotificationHistory");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRead = createBoolean("isRead");

    public final DateTimePath<java.time.LocalDateTime> readAt = createDateTime("readAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> receiverId = createComparable("receiverId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> senderId = createComparable("senderId", java.util.UUID.class);

    public final EnumPath<org.bodyguide_sv.notification.enums.NotificationType> type = createEnum("type", org.bodyguide_sv.notification.enums.NotificationType.class);

    public QUsersNotificationHistory(String variable) {
        super(UsersNotificationHistory.class, forVariable(variable));
    }

    public QUsersNotificationHistory(Path<? extends UsersNotificationHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersNotificationHistory(PathMetadata metadata) {
        super(UsersNotificationHistory.class, metadata);
    }

}

