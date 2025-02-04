package org.bodyguide_sv.activity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersActivityLog is a Querydsl query type for UsersActivityLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersActivityLog extends EntityPathBase<UsersActivityLog> {

    private static final long serialVersionUID = 1165875445L;

    public static final QUsersActivityLog usersActivityLog = new QUsersActivityLog("usersActivityLog");

    public final NumberPath<Integer> activityCode = createNumber("activityCode", Integer.class);

    public final NumberPath<Integer> dailyCount = createNumber("dailyCount", Integer.class);

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersActivityLog(String variable) {
        super(UsersActivityLog.class, forVariable(variable));
    }

    public QUsersActivityLog(Path<? extends UsersActivityLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersActivityLog(PathMetadata metadata) {
        super(UsersActivityLog.class, metadata);
    }

}

