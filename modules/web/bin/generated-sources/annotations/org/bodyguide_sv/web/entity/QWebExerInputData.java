package org.bodyguide_sv.web.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWebExerInputData is a Querydsl query type for WebExerInputData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWebExerInputData extends EntityPathBase<WebExerInputData> {

    private static final long serialVersionUID = 1176130069L;

    public static final QWebExerInputData webExerInputData = new QWebExerInputData("webExerInputData");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final NumberPath<Integer> benchReps = createNumber("benchReps", Integer.class);

    public final NumberPath<Double> benchWeight = createNumber("benchWeight", Double.class);

    public final StringPath clientIp = createString("clientIp");

    public final NumberPath<Integer> deadReps = createNumber("deadReps", Integer.class);

    public final NumberPath<Double> deadWeight = createNumber("deadWeight", Double.class);

    public final StringPath gender = createString("gender");

    public final NumberPath<Double> height = createNumber("height", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> overheadReps = createNumber("overheadReps", Integer.class);

    public final NumberPath<Double> overheadWeight = createNumber("overheadWeight", Double.class);

    public final NumberPath<Integer> pullupReps = createNumber("pullupReps", Integer.class);

    public final NumberPath<Integer> pushupReps = createNumber("pushupReps", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> recordAt = createDateTime("recordAt", java.time.LocalDateTime.class);

    public final NumberPath<Integer> squatReps = createNumber("squatReps", Integer.class);

    public final NumberPath<Double> squatWeight = createNumber("squatWeight", Double.class);

    public final ArrayPath<String[], String> supplePurpose = createArray("supplePurpose", String[].class);

    public final StringPath userAgent = createString("userAgent");

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QWebExerInputData(String variable) {
        super(WebExerInputData.class, forVariable(variable));
    }

    public QWebExerInputData(Path<? extends WebExerInputData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWebExerInputData(PathMetadata metadata) {
        super(WebExerInputData.class, metadata);
    }

}

