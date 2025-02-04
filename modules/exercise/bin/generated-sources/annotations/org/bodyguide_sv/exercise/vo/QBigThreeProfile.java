package org.bodyguide_sv.exercise.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBigThreeProfile is a Querydsl query type for BigThreeProfile
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QBigThreeProfile extends BeanPath<BigThreeProfile> {

    private static final long serialVersionUID = -126164272L;

    public static final QBigThreeProfile bigThreeProfile = new QBigThreeProfile("bigThreeProfile");

    public final NumberPath<Integer> reps = createNumber("reps", Integer.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final NumberPath<Double> weight = createNumber("weight", Double.class);

    public QBigThreeProfile(String variable) {
        super(BigThreeProfile.class, forVariable(variable));
    }

    public QBigThreeProfile(Path<? extends BigThreeProfile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBigThreeProfile(PathMetadata metadata) {
        super(BigThreeProfile.class, metadata);
    }

}

