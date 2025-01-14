package org.bodyguide_sv.exercise.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseRecordGroupListResponseWithHasNext;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseRecordGroupResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseRecordResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseSetResponse;
import org.bodyguide_sv.exercise.dto.ExerciseAnalysisData;
import org.bodyguide_sv.exercise.dto.MuscleGroupScoreDto;
import org.bodyguide_sv.exercise.entity.QExercise;
import org.bodyguide_sv.exercise.entity.QExercisePurpose;
import org.bodyguide_sv.exercise.entity.QExerciseThreshold;
import org.bodyguide_sv.exercise.entity.QMuscle;
import org.bodyguide_sv.exercise.entity.QMuscleGroup;
import org.bodyguide_sv.exercise.entity.QMuscleGroupDetail;
import org.bodyguide_sv.exercise.entity.QUsersExerciseBestScore;
import org.bodyguide_sv.exercise.entity.QUsersExerciseSetHistory;
import org.bodyguide_sv.exercise.enums.MuscleGroupType;
import org.bodyguide_sv.exercise.enums.ThresholdType;
import org.springframework.stereotype.Repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    // 운동 분석에 필요한 데이터 가져오기
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

    // 근육 그룹 그룹화해서 그룹의 최고점수 가져오기
    public List<MuscleGroupScoreDto> getMaxScoreAndExerciseIdByMuscleGroup(UUID userId) {
        QUsersExerciseBestScore bestScore = QUsersExerciseBestScore.usersExerciseBestScore;
        QExercise exercise = QExercise.exercise;
        QMuscleGroup muscleGroup = QMuscleGroup.muscleGroup;

        // 서브쿼리를 사용하여 최대 score 행을 선택
        QUsersExerciseBestScore subBestScore = new QUsersExerciseBestScore("subBestScore");

        List<Tuple> results = queryFactory.select(
                muscleGroup.groupId,
                bestScore.id.exerciseId,
                bestScore.score)
                .from(bestScore)
                .join(exercise).on(bestScore.id.exerciseId.eq(exercise.exerId))
                .join(muscleGroup).on(exercise.muscleId.eq(muscleGroup.groupId))
                .where(bestScore.id.userId.eq(userId)
                        .and(bestScore.id.in(
                                JPAExpressions.select(subBestScore.id)
                                        .from(subBestScore)
                                        .join(exercise).on(subBestScore.id.exerciseId.eq(exercise.exerId))
                                        .join(muscleGroup).on(exercise.muscleId.eq(muscleGroup.groupId))
                                        .where(subBestScore.id.userId.eq(userId))
                                        .groupBy(muscleGroup.groupId)
                                        .orderBy(subBestScore.score.desc())
                                        .limit(1))))
                .fetch();

        return results.stream()
                .map(tuple -> new MuscleGroupScoreDto(
                        MuscleGroupType.fromId(tuple.get(muscleGroup.groupId)), // groupId -> MuscleGroupType
                        tuple.get(bestScore.id.exerciseId), // exerciseId
                        tuple.get(bestScore.score) // maxScore
                ))
                .toList();
    }

    // 최근 n일치 ExerciseRecordGroupResponse 리스트 fetch (무한 스크롤 지원)
    public ExerciseRecordGroupListResponseWithHasNext fetchRecentDaysExerciseRecords(UUID userId, int days, int page, int size) {
        QUsersExerciseSetHistory setHistory = QUsersExerciseSetHistory.usersExerciseSetHistory;
        QUsersExerciseBestScore bestScore = QUsersExerciseBestScore.usersExerciseBestScore;

        // 1. groupId 페이징용 서브쿼리
        List<Integer> groupIds = queryFactory.select(setHistory.groupId)
                .from(setHistory)
                .where(setHistory.userId.eq(userId)
                        .and(setHistory.exerciseDate.after(LocalDateTime.now().minusDays(days))))
                .distinct() // 중복 제거
                .orderBy(setHistory.groupId.asc()) // groupId 기준 정렬
                .offset(page * size) // 페이징 시작 위치
                .limit(size + 1) // size + 1개로 다음 페이지 여부 확인
                        .fetch();

        log.debug(groupIds.toString());

        // 다음 페이지 여부 판단
        boolean hasNext = groupIds.size() > size;
        if (hasNext) {
            groupIds = groupIds.subList(0, size); // 필요한 만큼만 잘라냄
        }

        // 2. 해당 groupId에 속한 데이터만 조회
        List<ExerciseRecordGroupResponse> results = queryFactory.select(
                        Projections.constructor(
                                ExerciseRecordGroupResponse.class,
                                setHistory.groupId,
                                setHistory.exerciseDate,
                                Projections.list(
                                        Projections.constructor(
                                                ExerciseRecordResponse.class,
                                                setHistory.exerciseId,
                                                Projections.list(
                                                        Projections.constructor(
                                                                ExerciseSetResponse.class,
                                                                setHistory.set,
                                                                setHistory.weight,
                                                                setHistory.reps,
                                                                setHistory.score,
                                                                setHistory.strength)),
                                                bestScore.weight,
                                                bestScore.reps))))
                .from(setHistory)
                .leftJoin(bestScore)
                .on(setHistory.userId.eq(bestScore.id.userId)
                        .and(setHistory.exerciseId.eq(bestScore.id.exerciseId)))
                .where(setHistory.groupId.in(groupIds)) // 가져온 groupId로 필터링
                .orderBy(setHistory.groupId.asc(), setHistory.exerciseDate.desc()) // 정렬
                .fetch();

        // 3. 결과 반환
        return new ExerciseRecordGroupListResponseWithHasNext(
                hasNext,
                results
        );
    }
    
    // 특정 month의 ExerciseRecordGroupResponse 리스트 fetch
    public ExerciseRecordGroupListResponseWithHasNext fetchMonthlyExerciseRecords(UUID userId, int year, int month, int page, int size) {
        QUsersExerciseSetHistory setHistory = QUsersExerciseSetHistory.usersExerciseSetHistory;
        QUsersExerciseBestScore bestScore = QUsersExerciseBestScore.usersExerciseBestScore;
    
        // 해당 월의 시작일과 종료일 계산
        LocalDateTime startOfMonth = LocalDateTime.of(year, month, 1, 0, 0);
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
    
        // 1. 해당 월에 속한 groupId만 페이징 처리
        List<Integer> groupIds = queryFactory.select(setHistory.groupId)
                .from(setHistory)
                .where(setHistory.userId.eq(userId)
                        .and(setHistory.exerciseDate.between(startOfMonth, endOfMonth)))
                .distinct() // 중복 제거
                .orderBy(setHistory.groupId.asc()) // groupId 기준 정렬
                .offset(page * size) // 페이징 시작 위치
                .limit(size + 1) // size + 1개로 다음 페이지 여부 확인
                .fetch();
    
        // 다음 페이지 여부 판단
        boolean hasNext = groupIds.size() > size;
        if (hasNext) {
            groupIds = groupIds.subList(0, size); // 필요한 만큼만 잘라냄
        }
    
        // 2. 해당 groupId에 속한 데이터만 조회
        List<ExerciseRecordGroupResponse> results = queryFactory.select(
                        Projections.constructor(
                                ExerciseRecordGroupResponse.class,
                                setHistory.groupId,
                                setHistory.exerciseDate,
                                Projections.list(
                                        Projections.constructor(
                                                ExerciseRecordResponse.class,
                                                setHistory.exerciseId,
                                                Projections.list(
                                                        Projections.constructor(
                                                                ExerciseSetResponse.class,
                                                                setHistory.set,
                                                                setHistory.weight,
                                                                setHistory.reps,
                                                                setHistory.score,
                                                                setHistory.strength)),
                                                bestScore.weight,
                                                bestScore.reps))))
                .from(setHistory)
                .leftJoin(bestScore)
                .on(setHistory.userId.eq(bestScore.id.userId)
                        .and(setHistory.exerciseId.eq(bestScore.id.exerciseId)))
                .where(setHistory.groupId.in(groupIds)) // 추출된 groupId로 필터링
                .orderBy(setHistory.groupId.asc(), setHistory.exerciseDate.asc(), setHistory.set.asc()) // 정렬
                .fetch();
    
        // 3. 결과 반환
        return new ExerciseRecordGroupListResponseWithHasNext(
                hasNext,
                results
        );
    }

}
