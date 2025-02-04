package org.bodyguide_sv.nutrition.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersNutritionProfileHistory is a Querydsl query type for UsersNutritionProfileHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersNutritionProfileHistory extends EntityPathBase<UsersNutritionProfileHistory> {

    private static final long serialVersionUID = -1003751372L;

    public static final QUsersNutritionProfileHistory usersNutritionProfileHistory = new QUsersNutritionProfileHistory("usersNutritionProfileHistory");

    public final NumberPath<Double> carbCal = createNumber("carbCal", Double.class);

    public final NumberPath<Integer> dietId = createNumber("dietId", Integer.class);

    public final NumberPath<Double> fatCal = createNumber("fatCal", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> pa = createNumber("pa", Integer.class);

    public final NumberPath<Double> proteinCal = createNumber("proteinCal", Double.class);

    public final DateTimePath<java.time.LocalDateTime> recordAt = createDateTime("recordAt", java.time.LocalDateTime.class);

    public final StringPath sleepTime = createString("sleepTime");

    public final NumberPath<Double> targetCalory = createNumber("targetCalory", Double.class);

    public final NumberPath<Double> targetWeight = createNumber("targetWeight", Double.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public final StringPath wakeupTime = createString("wakeupTime");

    public QUsersNutritionProfileHistory(String variable) {
        super(UsersNutritionProfileHistory.class, forVariable(variable));
    }

    public QUsersNutritionProfileHistory(Path<? extends UsersNutritionProfileHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersNutritionProfileHistory(PathMetadata metadata) {
        super(UsersNutritionProfileHistory.class, metadata);
    }

}

