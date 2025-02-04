package org.bodyguide_sv.coupang.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCoupangRecommendFood is a Querydsl query type for CoupangRecommendFood
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupangRecommendFood extends EntityPathBase<CoupangRecommendFood> {

    private static final long serialVersionUID = -383109925L;

    public static final QCoupangRecommendFood coupangRecommendFood = new QCoupangRecommendFood("coupangRecommendFood");

    public final NumberPath<Integer> dietType = createNumber("dietType", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> nutrientType = createNumber("nutrientType", Integer.class);

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public QCoupangRecommendFood(String variable) {
        super(CoupangRecommendFood.class, forVariable(variable));
    }

    public QCoupangRecommendFood(Path<? extends CoupangRecommendFood> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCoupangRecommendFood(PathMetadata metadata) {
        super(CoupangRecommendFood.class, metadata);
    }

}

