package org.bodyguide_sv.coupang.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCoupangRecommendSupplements is a Querydsl query type for CoupangRecommendSupplements
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupangRecommendSupplements extends EntityPathBase<CoupangRecommendSupplements> {

    private static final long serialVersionUID = -1373175875L;

    public static final QCoupangRecommendSupplements coupangRecommendSupplements = new QCoupangRecommendSupplements("coupangRecommendSupplements");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final NumberPath<Integer> relatedNutrientId = createNumber("relatedNutrientId", Integer.class);

    public QCoupangRecommendSupplements(String variable) {
        super(CoupangRecommendSupplements.class, forVariable(variable));
    }

    public QCoupangRecommendSupplements(Path<? extends CoupangRecommendSupplements> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCoupangRecommendSupplements(PathMetadata metadata) {
        super(CoupangRecommendSupplements.class, metadata);
    }

}

