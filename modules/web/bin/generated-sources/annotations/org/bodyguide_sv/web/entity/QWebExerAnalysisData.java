package org.bodyguide_sv.web.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWebExerAnalysisData is a Querydsl query type for WebExerAnalysisData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWebExerAnalysisData extends EntityPathBase<WebExerAnalysisData> {

    private static final long serialVersionUID = -135593595L;

    public static final QWebExerAnalysisData webExerAnalysisData = new QWebExerAnalysisData("webExerAnalysisData");

    public final NumberPath<Double> bench1RM = createNumber("bench1RM", Double.class);

    public final NumberPath<Double> benchScore = createNumber("benchScore", Double.class);

    public final NumberPath<Double> bigThree = createNumber("bigThree", Double.class);

    public final NumberPath<Double> dead1RM = createNumber("dead1RM", Double.class);

    public final NumberPath<Double> deadScore = createNumber("deadScore", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> overhead1RM = createNumber("overhead1RM", Double.class);

    public final NumberPath<Double> overheadScore = createNumber("overheadScore", Double.class);

    public final NumberPath<Double> pullupScore = createNumber("pullupScore", Double.class);

    public final NumberPath<Double> pushupScore = createNumber("pushupScore", Double.class);

    public final NumberPath<Double> squat1RM = createNumber("squat1RM", Double.class);

    public final NumberPath<Double> squatScore = createNumber("squatScore", Double.class);

    public final NumberPath<Double> totalScore = createNumber("totalScore", Double.class);

    public QWebExerAnalysisData(String variable) {
        super(WebExerAnalysisData.class, forVariable(variable));
    }

    public QWebExerAnalysisData(Path<? extends WebExerAnalysisData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWebExerAnalysisData(PathMetadata metadata) {
        super(WebExerAnalysisData.class, metadata);
    }

}

