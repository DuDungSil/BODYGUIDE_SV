package org.bodyguide_sv.exercise.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMuscleGroupDetail is a Querydsl query type for MuscleGroupDetail
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMuscleGroupDetail extends EntityPathBase<MuscleGroupDetail> {

    private static final long serialVersionUID = 1702903150L;

    public static final QMuscleGroupDetail muscleGroupDetail = new QMuscleGroupDetail("muscleGroupDetail");

    public final NumberPath<Integer> detailId = createNumber("detailId", Integer.class);

    public final StringPath detailMuscle = createString("detailMuscle");

    public final NumberPath<Integer> groupId = createNumber("groupId", Integer.class);

    public QMuscleGroupDetail(String variable) {
        super(MuscleGroupDetail.class, forVariable(variable));
    }

    public QMuscleGroupDetail(Path<? extends MuscleGroupDetail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMuscleGroupDetail(PathMetadata metadata) {
        super(MuscleGroupDetail.class, metadata);
    }

}

