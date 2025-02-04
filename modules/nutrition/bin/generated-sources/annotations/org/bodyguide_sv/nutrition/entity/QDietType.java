package org.bodyguide_sv.nutrition.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDietType is a Querydsl query type for DietType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDietType extends EntityPathBase<DietType> {

    private static final long serialVersionUID = 669766343L;

    public static final QDietType dietType = new QDietType("dietType");

    public final NumberPath<Double> carbohydrate = createNumber("carbohydrate", Double.class);

    public final NumberPath<Integer> dietId = createNumber("dietId", Integer.class);

    public final StringPath dietName = createString("dietName");

    public final NumberPath<Double> protein = createNumber("protein", Double.class);

    public final NumberPath<Double> satFat = createNumber("satFat", Double.class);

    public final NumberPath<Double> unFat = createNumber("unFat", Double.class);

    public QDietType(String variable) {
        super(DietType.class, forVariable(variable));
    }

    public QDietType(Path<? extends DietType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDietType(PathMetadata metadata) {
        super(DietType.class, metadata);
    }

}

