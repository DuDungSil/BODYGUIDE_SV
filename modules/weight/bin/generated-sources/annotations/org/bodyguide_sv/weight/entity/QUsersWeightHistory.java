package org.bodyguide_sv.weight.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersWeightHistory is a Querydsl query type for UsersWeightHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersWeightHistory extends EntityPathBase<UsersWeightHistory> {

    private static final long serialVersionUID = -343621787L;

    public static final QUsersWeightHistory usersWeightHistory = new QUsersWeightHistory("usersWeightHistory");

    public final NumberPath<Long> historyId = createNumber("historyId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> recordDate = createDateTime("recordDate", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QUsersWeightHistory(String variable) {
        super(UsersWeightHistory.class, forVariable(variable));
    }

    public QUsersWeightHistory(Path<? extends UsersWeightHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersWeightHistory(PathMetadata metadata) {
        super(UsersWeightHistory.class, metadata);
    }

}

