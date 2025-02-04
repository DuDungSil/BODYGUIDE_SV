package org.bodyguide_sv.calendar.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersCalendarMemoHistory is a Querydsl query type for UsersCalendarMemoHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersCalendarMemoHistory extends EntityPathBase<UsersCalendarMemoHistory> {

    private static final long serialVersionUID = 1304676171L;

    public static final QUsersCalendarMemoHistory usersCalendarMemoHistory = new QUsersCalendarMemoHistory("usersCalendarMemoHistory");

    public final NumberPath<Long> historyId = createNumber("historyId", Long.class);

    public final StringPath note = createString("note");

    public final DatePath<java.time.LocalDate> noteDate = createDate("noteDate", java.time.LocalDate.class);

    public final DateTimePath<java.time.LocalDateTime> recordAt = createDateTime("recordAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersCalendarMemoHistory(String variable) {
        super(UsersCalendarMemoHistory.class, forVariable(variable));
    }

    public QUsersCalendarMemoHistory(Path<? extends UsersCalendarMemoHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersCalendarMemoHistory(PathMetadata metadata) {
        super(UsersCalendarMemoHistory.class, metadata);
    }

}

