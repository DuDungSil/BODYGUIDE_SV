package org.bodyguide_sv.nutrition.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QNutrientInfo is a Querydsl query type for NutrientInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QNutrientInfo extends EntityPathBase<NutrientInfo> {

    private static final long serialVersionUID = -1524808146L;

    public static final QNutrientInfo nutrientInfo = new QNutrientInfo("nutrientInfo");

    public final StringPath function = createString("function");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath intake = createString("intake");

    public final StringPath name = createString("name");

    public final StringPath precaution = createString("precaution");

    public final StringPath sideEffect = createString("sideEffect");

    public final StringPath summary = createString("summary");

    public final StringPath timing = createString("timing");

    public QNutrientInfo(String variable) {
        super(NutrientInfo.class, forVariable(variable));
    }

    public QNutrientInfo(Path<? extends NutrientInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNutrientInfo(PathMetadata metadata) {
        super(NutrientInfo.class, metadata);
    }

}

