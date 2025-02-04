package org.bodyguide_sv.calendar.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUsersIntakeHistory is a Querydsl query type for UsersIntakeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsersIntakeHistory extends EntityPathBase<UsersIntakeHistory> {

    private static final long serialVersionUID = 2036868727L;

    public static final QUsersIntakeHistory usersIntakeHistory = new QUsersIntakeHistory("usersIntakeHistory");

    public final StringPath brand = createString("brand");

    public final NumberPath<Double> calory = createNumber("calory", Double.class);

    public final NumberPath<Double> carbohydrate = createNumber("carbohydrate", Double.class);

    public final NumberPath<Double> cholesterol = createNumber("cholesterol", Double.class);

    public final NumberPath<Double> dietaryFiber = createNumber("dietaryFiber", Double.class);

    public final NumberPath<Double> fat = createNumber("fat", Double.class);

    public final StringPath foodName = createString("foodName");

    public final NumberPath<Long> historyId = createNumber("historyId", Long.class);

    public final DatePath<java.time.LocalDate> intakeDate = createDate("intakeDate", java.time.LocalDate.class);

    public final NumberPath<Double> protein = createNumber("protein", Double.class);

    public final DateTimePath<java.time.LocalDateTime> recordAt = createDateTime("recordAt", java.time.LocalDateTime.class);

    public final NumberPath<Double> saturatedFat = createNumber("saturatedFat", Double.class);

    public final NumberPath<Double> servingAmount = createNumber("servingAmount", Double.class);

    public final StringPath servingUnit = createString("servingUnit");

    public final NumberPath<Double> sodium = createNumber("sodium", Double.class);

    public final NumberPath<Double> sugar = createNumber("sugar", Double.class);

    public final NumberPath<Double> transFat = createNumber("transFat", Double.class);

    public final NumberPath<Double> unsaturatedFat = createNumber("unsaturatedFat", Double.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QUsersIntakeHistory(String variable) {
        super(UsersIntakeHistory.class, forVariable(variable));
    }

    public QUsersIntakeHistory(Path<? extends UsersIntakeHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersIntakeHistory(PathMetadata metadata) {
        super(UsersIntakeHistory.class, metadata);
    }

}

