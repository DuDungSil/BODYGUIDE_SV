package org.bodyguide_sv.notification.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSystemNotification is a Querydsl query type for SystemNotification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSystemNotification extends EntityPathBase<SystemNotification> {

    private static final long serialVersionUID = 519491070L;

    public static final QSystemNotification systemNotification = new QSystemNotification("systemNotification");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> expiresAt = createDateTime("expiresAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath url = createString("url");

    public QSystemNotification(String variable) {
        super(SystemNotification.class, forVariable(variable));
    }

    public QSystemNotification(Path<? extends SystemNotification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSystemNotification(PathMetadata metadata) {
        super(SystemNotification.class, metadata);
    }

}

