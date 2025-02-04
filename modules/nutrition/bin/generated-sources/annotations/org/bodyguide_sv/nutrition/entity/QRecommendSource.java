package org.bodyguide_sv.nutrition.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRecommendSource is a Querydsl query type for RecommendSource
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRecommendSource extends EntityPathBase<RecommendSource> {

    private static final long serialVersionUID = -1671782306L;

    public static final QRecommendSource recommendSource = new QRecommendSource("recommendSource");

    public final NumberPath<Integer> dietTypeId = createNumber("dietTypeId", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final NumberPath<Integer> nutrientTypeId = createNumber("nutrientTypeId", Integer.class);

    public final StringPath source = createString("source");

    public QRecommendSource(String variable) {
        super(RecommendSource.class, forVariable(variable));
    }

    public QRecommendSource(Path<? extends RecommendSource> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRecommendSource(PathMetadata metadata) {
        super(RecommendSource.class, metadata);
    }

}

