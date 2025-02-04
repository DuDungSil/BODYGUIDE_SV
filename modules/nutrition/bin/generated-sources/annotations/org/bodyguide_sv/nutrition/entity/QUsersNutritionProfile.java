package org.bodyguide_sv.nutrition.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersNutritionProfile is a Querydsl query type for UsersNutritionProfile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersNutritionProfile extends EntityPathBase<UsersNutritionProfile> {

    private static final long serialVersionUID = 1302084672L;

    public static final QUsersNutritionProfile usersNutritionProfile = new QUsersNutritionProfile("usersNutritionProfile");

    public final NumberPath<Double> carbCal = createNumber("carbCal", Double.class);

    public final NumberPath<Integer> dietId = createNumber("dietId", Integer.class);

    public final NumberPath<Double> fatCal = createNumber("fatCal", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> pa = createNumber("pa", Integer.class);

    public final NumberPath<Double> proteinCal = createNumber("proteinCal", Double.class);

    public final StringPath sleepTime = createString("sleepTime");

    public final NumberPath<Double> targetCalory = createNumber("targetCalory", Double.class);

    public final NumberPath<Double> targetWeight = createNumber("targetWeight", Double.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public final StringPath wakeupTime = createString("wakeupTime");

    public QUsersNutritionProfile(String variable) {
        super(UsersNutritionProfile.class, forVariable(variable));
    }

    public QUsersNutritionProfile(Path<? extends UsersNutritionProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersNutritionProfile(PathMetadata metadata) {
        super(UsersNutritionProfile.class, metadata);
    }

}

