package org.bodyguide_sv.exercise.repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bodyguide_sv.exercise.dto.ExerciseAnalysisData;
import org.bodyguide_sv.exercise.dto.MuscleGroupScoreDto;
import org.bodyguide_sv.exercise.entity.QExercise;
import org.bodyguide_sv.exercise.entity.QExercisePurpose;
import org.bodyguide_sv.exercise.entity.QExerciseThreshold;
import org.bodyguide_sv.exercise.entity.QMuscle;
import org.bodyguide_sv.exercise.entity.QMuscleGroup;
import org.bodyguide_sv.exercise.entity.QMuscleGroupDetail;
import org.bodyguide_sv.exercise.entity.QUsersExerciseBestScore;
import org.bodyguide_sv.exercise.enums.MuscleGroupType;
import org.bodyguide_sv.exercise.enums.ThresholdType;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ExerciseQueryRepository {
    
    private final JPAQueryFactory queryFactory;

    // 운동 목적 가져오기
    public List<String> findAllPurposes() {
        QExercisePurpose exercisePurpose = QExercisePurpose.exercisePurpose;

        return queryFactory
                .select(exercisePurpose.purpose)
                .from(exercisePurpose)
                .fetch();
    }

    public ExerciseAnalysisData findExerciseData(int exerciseId, String gender) {
        QExercise exercise = QExercise.exercise;
        QExerciseThreshold exerciseThreshold = QExerciseThreshold.exerciseThreshold;
        QMuscle muscle = QMuscle.muscle;
        QMuscleGroup muscleGroup = QMuscleGroup.muscleGroup;
    
        // 한 번의 쿼리로 모든 데이터 조회
        List<Tuple> results = queryFactory.select(
                muscleGroup.groupId,                  // 근육 그룹 id
                exercise.thresholdType,               // Threshold Type
                exerciseThreshold.threshold           // Thresholds
            )
            .from(exercise)
            .join(muscle).on(exercise.muscleId.eq(muscle.muscleId))
            .join(muscleGroup).on(muscle.muscleGroupId.eq(muscleGroup.groupId))
            .join(exerciseThreshold).on(exerciseThreshold.exerId.eq(exercise.exerId))
            .where(
                exercise.exerId.eq(exerciseId),
                exerciseThreshold.gender.eq(gender),
                exerciseThreshold.rank.loe(7) // RANK <= 7
            )
            .orderBy(exerciseThreshold.rank.asc())
            .fetch();
    
        // 결과 매핑
        Integer groupId = results.get(0).get(muscleGroup.groupId); // NumberPath<Integer>
        Integer thresholdTypeId = results.get(0).get(exercise.thresholdType); // NumberPath<Integer>
    
        MuscleGroupType muscleGroupType = MuscleGroupType.fromId(groupId);
        ThresholdType thresholdType = ThresholdType.fromId(thresholdTypeId);
    
        List<Double> thresholds = results.stream()
                                            .map(tuple -> tuple.get(2, Double.class))
                                            .toList();
    
        // record 생성
        return new ExerciseAnalysisData(muscleGroupType, thresholdType, thresholds);
    }

    // 운동 이름으로 운동 점수 임계점 가져오기
    public List<Double> findThresholds(int exerciseId, String gender) {
        QExercise exercise = QExercise.exercise;
        QExerciseThreshold exerciseThreshold = QExerciseThreshold.exerciseThreshold;

        return queryFactory
                .select(exerciseThreshold.threshold)
                .from(exerciseThreshold)
                .join(exercise).on(exercise.exerId.eq(exerciseId))
                .where(
                        exerciseThreshold.exerId.eq(exercise.exerId),
                        exerciseThreshold.gender.eq(gender),
                        exerciseThreshold.rank.loe(7) // RANK <= 7
                )
                .orderBy(exerciseThreshold.rank.asc()) // ORDER BY RANK ASC
                .fetch();
    }

    // 운동 임계점 유형 가져오기
    public int findThresholdTypeByExerName(int exerciseId) {
        QExercise exercise = QExercise.exercise;

        return queryFactory
                .select(exercise.thresholdType)
                .from(exercise)
                .where(exercise.exerId.eq(exerciseId)) // WHERE EXER_NAME = #{EXER_NAME}
                .fetchOne(); // 단일 결과를 반환
    }

    // 운동 id로 운동 근육 그룹 이름 가져오기
    public String findMuscleGroupIdNameByExerName(int exerciseId) {
        QExercise exercise = QExercise.exercise;
        QMuscle muscle = QMuscle.muscle;
        QMuscleGroup muscleGroup = QMuscleGroup.muscleGroup;

        return queryFactory.select(muscleGroup.groupName)
                .from(exercise)
                .join(muscle).on(exercise.muscleId.eq(muscle.muscleId))
                .join(muscleGroup).on(muscle.muscleGroupId.eq(muscleGroup.groupId))
                .where(exercise.exerId.eq(exerciseId))
                .fetchOne();
    }

    // 운동 근육 그룹 id로 관련 strength 가져오기
    public String findStrengthByMuscleGroupName(int muscleGroupId) {
        QMuscleGroup muscleGroup = QMuscleGroup.muscleGroup;

        return queryFactory
                .select(muscleGroup.strength)
                .from(muscleGroup)
                .where(muscleGroup.groupId.eq(muscleGroupId)) // WHERE EXER_NAME = #{EXER_NAME}
                .fetchOne(); // 단일 결과를 반환
    }

    // 운동 근육 그룹 id로 관련 세부 근육 부위 가져오기
    public List<String> findMuscleDetailsByMuscleGroupName(int muscleGroupId) {
        QMuscleGroup muscleGroup = QMuscleGroup.muscleGroup;
        QMuscleGroupDetail muscleGroupDetail = QMuscleGroupDetail.muscleGroupDetail;

        return queryFactory.select(muscleGroupDetail.detailMuscle)
                .from(muscleGroup)
                .join(muscleGroupDetail).on(muscleGroup.groupId.eq(muscleGroupDetail.groupId))
                .where(muscleGroup.groupId.eq(muscleGroupId))
                .fetch();
    }

    public List<MuscleGroupScoreDto> getMaxScoreByMuscleGroup(UUID userId) {
		QUsersExerciseBestScore bestScore = QUsersExerciseBestScore.usersExerciseBestScore;
		QExercise exercise = QExercise.exercise;
		QMuscleGroup muscleGroup = QMuscleGroup.muscleGroup;
	
		// 서브쿼리를 사용하여 최대 score 행을 선택
		QUsersExerciseBestScore subBestScore = new QUsersExerciseBestScore("subBestScore");

		List<Tuple> results = queryFactory.select(
								muscleGroup.groupId,
								bestScore.id.exerciseId,
								bestScore.score
								)
								.from(bestScore)
								.join(exercise).on(bestScore.id.exerciseId.eq(exercise.exerId))
								.join(muscleGroup).on(exercise.muscleId.eq(muscleGroup.groupId))
								.where(bestScore.id.userId.eq(userId)
									.and(bestScore.score.eq(
											JPAExpressions.select(subBestScore.score.max())
													.from(subBestScore)
													.join(exercise).on(subBestScore.id.exerciseId.eq(exercise.exerId))
													.join(muscleGroup).on(exercise.muscleId.eq(muscleGroup.groupId))
													.where(subBestScore.id.userId.eq(userId))
													.groupBy(muscleGroup.groupId)
			))
		)
		.fetch();
			
		return results.stream()
		.map(tuple -> new MuscleGroupScoreDto(
				MuscleGroupType.fromId(tuple.get(muscleGroup.groupId)), // groupId -> MuscleGroupType
				tuple.get(bestScore.id.exerciseId), // exerciseId
				tuple.get(bestScore.score)   // maxScore
		))
		.toList();
    }

}
