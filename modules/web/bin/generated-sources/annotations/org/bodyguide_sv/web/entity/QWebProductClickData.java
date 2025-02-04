package org.bodyguide_sv.web.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWebProductClickData is a Querydsl query type for WebProductClickData
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWebProductClickData extends EntityPathBase<WebProductClickData> {

    private static final long serialVersionUID = -2144423614L;

    public static final QWebProductClickData webProductClickData = new QWebProductClickData("webProductClickData");

    public final StringPath clientIp = createString("clientIp");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> productId = createNumber("productId", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> recordAt = createDateTime("recordAt", java.time.LocalDateTime.class);

    public final StringPath userAgent = createString("userAgent");

    public QWebProductClickData(String variable) {
        super(WebProductClickData.class, forVariable(variable));
    }

    public QWebProductClickData(Path<? extends WebProductClickData> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWebProductClickData(PathMetadata metadata) {
        super(WebProductClickData.class, metadata);
    }

}

