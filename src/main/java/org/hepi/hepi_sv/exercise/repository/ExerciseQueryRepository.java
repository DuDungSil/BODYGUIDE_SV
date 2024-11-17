package org.hepi.hepi_sv.exercise.repository;

import java.util.List;

import org.hepi.hepi_sv.exercise.entity.QBodyPart;
import org.hepi.hepi_sv.exercise.entity.QExercise;
import org.hepi.hepi_sv.exercise.entity.QExercisePurpose;
import org.hepi.hepi_sv.exercise.entity.QExerciseThreshold;
import org.hepi.hepi_sv.exercise.entity.QMuscleGroup;
import org.hepi.hepi_sv.exercise.entity.QMuscleGroupDetail;
import org.springframework.stereotype.Repository;

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

    // 운동 점수 임계점 가져오기
    public List<Double> findThresholds(String exerName, String gender) {
        QExerciseThreshold exerciseThreshold = QExerciseThreshold.exerciseThreshold;

        return queryFactory
                .select(exerciseThreshold.threshold)
                .from(exerciseThreshold)
                .where(
                        exerciseThreshold.exerName.eq(exerName),
                        exerciseThreshold.gender.eq(gender),
                        exerciseThreshold.rank.loe(7) // RANK <= 7
                )
                .orderBy(exerciseThreshold.rank.asc()) // ORDER BY RANK ASC
                .fetch();
    }

    // 운동 임계점 유형 가져오기
    public int findThresholdTypeByExerName(String exerName) {
        QExercise exercise = QExercise.exercise;

        return queryFactory
                .select(exercise.thresholdType)
                .from(exercise)
                .where(exercise.exerName.eq(exerName)) // WHERE EXER_NAME = #{EXER_NAME}
                .fetchOne(); // 단일 결과를 반환
    }

    // 운동 근육 그룹 가져오기
    public String findMuscleGroupByExerName(String exerName) {
        QExercise exercise = QExercise.exercise;
        QBodyPart bodyPart = QBodyPart.bodyPart;

        return queryFactory
                .select(bodyPart.muscleGroup)
                .from(exercise)
                .join(bodyPart).on(exercise.bodyPart.eq(bodyPart.partName)) // JOIN 조건
                .fetchOne(); // 단일 결과를 반환
    }

    // 운동 근육 그룹의 관련 strength 가져오기
    public String findStrengthByMuscleGroupName(String muscleGroupName) {
        QMuscleGroup muscleGroup = QMuscleGroup.muscleGroup;

        return queryFactory
                .select(muscleGroup.strength)
                .from(muscleGroup)
                .where(muscleGroup.groupName.eq(muscleGroupName)) // WHERE EXER_NAME = #{EXER_NAME}
                .fetchOne(); // 단일 결과를 반환
    }

    // 운동 근육 그룹의 관련 세부 근육 부위 가져오기
    public List<String> findMuscleDetailsByMuscleGroupName(String muscleGroupName) {
        QMuscleGroupDetail muscleGroupDetail = QMuscleGroupDetail.muscleGroupDetail;

        return queryFactory
                .select(muscleGroupDetail.detailMuscle)
                .from(muscleGroupDetail)
                .where(muscleGroupDetail.groupName.eq(muscleGroupName)) 
                .fetch(); 
    }

}
