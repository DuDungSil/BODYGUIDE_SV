package org.bodyguide_sv.web.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWebNutriInputData is a Querydsl query type for WebNutriInputData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWebNutriInputData extends EntityPathBase<WebNutriInputData> {

    private static final long serialVersionUID = 1563428943L;

    public static final QWebNutriInputData webNutriInputData = new QWebNutriInputData("webNutriInputData");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final StringPath clientIp = createString("clientIp");

    public final StringPath dietGoal = createString("dietGoal");

    public final StringPath dietType = createString("dietType");

    public final StringPath gender = createString("gender");

    public final NumberPath<Double> height = createNumber("height", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath pa = createString("pa");

    public final DateTimePath<java.time.LocalDateTime> recordAt = createDateTime("recordAt", java.time.LocalDateTime.class);

    public final StringPath sleep = createString("sleep");

    public final StringPath userAgent = createString("userAgent");

    public final StringPath wakeup = createString("wakeup");

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QWebNutriInputData(String variable) {
        super(WebNutriInputData.class, forVariable(variable));
    }

    public QWebNutriInputData(Path<? extends WebNutriInputData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWebNutriInputData(PathMetadata metadata) {
        super(WebNutriInputData.class, metadata);
    }

}

