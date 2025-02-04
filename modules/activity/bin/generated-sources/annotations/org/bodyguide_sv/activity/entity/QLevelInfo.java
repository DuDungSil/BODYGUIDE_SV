package org.bodyguide_sv.activity.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLevelInfo is a Querydsl query type for LevelInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLevelInfo extends EntityPathBase<LevelInfo> {

    private static final long serialVersionUID = -780055318L;

    public static final QLevelInfo levelInfo = new QLevelInfo("levelInfo");

    public final NumberPath<Double> discountRate = createNumber("discountRate", Double.class);

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final NumberPath<Integer> nextLevelExp = createNumber("nextLevelExp", Integer.class);

    public final NumberPath<Integer> totalExp = createNumber("totalExp", Integer.class);

    public QLevelInfo(String variable) {
        super(LevelInfo.class, forVariable(variable));
    }

    public QLevelInfo(Path<? extends LevelInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLevelInfo(PathMetadata metadata) {
        super(LevelInfo.class, metadata);
    }

}

