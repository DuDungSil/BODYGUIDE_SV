package org.bodyguide_sv.web.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWebNutriAnalysisData is a Querydsl query type for WebNutriAnalysisData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWebNutriAnalysisData extends EntityPathBase<WebNutriAnalysisData> {

    private static final long serialVersionUID = 1603004683L;

    public static final QWebNutriAnalysisData webNutriAnalysisData = new QWebNutriAnalysisData("webNutriAnalysisData");

    public final NumberPath<Double> bmi = createNumber("bmi", Double.class);

    public final NumberPath<Double> bmr = createNumber("bmr", Double.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> targetCalory = createNumber("targetCalory", Double.class);

    public final NumberPath<Double> tdee = createNumber("tdee", Double.class);

    public QWebNutriAnalysisData(String variable) {
        super(WebNutriAnalysisData.class, forVariable(variable));
    }

    public QWebNutriAnalysisData(Path<? extends WebNutriAnalysisData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWebNutriAnalysisData(PathMetadata metadata) {
        super(WebNutriAnalysisData.class, metadata);
    }

}

