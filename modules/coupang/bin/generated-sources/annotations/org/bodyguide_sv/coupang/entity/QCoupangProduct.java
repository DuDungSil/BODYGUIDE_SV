package org.bodyguide_sv.coupang.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCoupangProduct is a Querydsl query type for CoupangProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCoupangProduct extends EntityPathBase<CoupangProduct> {

    private static final long serialVersionUID = 2036153168L;

    public static final QCoupangProduct coupangProduct = new QCoupangProduct("coupangProduct");

    public final StringPath brand = createString("brand");

    public final StringPath category = createString("category");

    public final NumberPath<Integer> discount = createNumber("discount", Integer.class);

    public final StringPath imgUrl = createString("imgUrl");

    public final BooleanPath isRocketDelivery = createBoolean("isRocketDelivery");

    public final BooleanPath isRocketFresh = createBoolean("isRocketFresh");

    public final StringPath keyword = createString("keyword");

    public final StringPath name = createString("name");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public final NumberPath<Integer> review = createNumber("review", Integer.class);

    public final NumberPath<Integer> salePrice = createNumber("salePrice", Integer.class);

    public final StringPath url = createString("url");

    public QCoupangProduct(String variable) {
        super(CoupangProduct.class, forVariable(variable));
    }

    public QCoupangProduct(Path<? extends CoupangProduct> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCoupangProduct(PathMetadata metadata) {
        super(CoupangProduct.class, metadata);
    }

}

