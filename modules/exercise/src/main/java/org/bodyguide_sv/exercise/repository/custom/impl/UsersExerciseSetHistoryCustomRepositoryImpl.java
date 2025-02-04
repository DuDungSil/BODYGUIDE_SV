package org.bodyguide_sv.exercise.repository.custom.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseRecordGroupListResponseWithHasNext;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseRecordGroupResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseRecordResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseSetResponse;
import org.bodyguide_sv.exercise.dto.MaxScoreAndWeightAndRepsDTO;
import org.bodyguide_sv.exercise.dto.MaxWeightAndRepsDTO;
import org.bodyguide_sv.exercise.entity.QUsersExerciseBestScore;
import org.bodyguide_sv.exercise.entity.QUsersExerciseSetHistory;
import org.bodyguide_sv.exercise.entity.UsersExerciseSetHistory;
import org.bodyguide_sv.exercise.repository.custom.UsersExerciseSetHistoryCustomRepository;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UsersExerciseSetHistoryCustomRepositoryImpl implements UsersExerciseSetHistoryCustomRepository {
    
    private final JPAQueryFactory queryFactory;

    @Override
    public MaxWeightAndRepsDTO findMaxWeightAndRepsByUserIdAndExerciseId(UUID userId, int exerciseId) {
        QUsersExerciseSetHistory history = QUsersExerciseSetHistory.usersExerciseSetHistory;

        return queryFactory.select(
                Projections.constructor(MaxWeightAndRepsDTO.class, history.weight, history.reps))
                .from(history)
                .where(
                        history.userId.eq(userId),
                        history.exerciseId.eq(exerciseId))
                .orderBy(history.weight.desc(), history.reps.desc())
                .limit(1)
                .fetchOne();
    }
    
    @Override
    public MaxScoreAndWeightAndRepsDTO findMaxScoreByUserIdAndExerciseId(UUID userId, int exerciseId) {
        QUsersExerciseSetHistory history = QUsersExerciseSetHistory.usersExerciseSetHistory;

        return queryFactory.select(
            Projections.constructor(MaxScoreAndWeightAndRepsDTO.class, history.weight, history.reps, history.score))
            .from(history)
            .where(
                    history.userId.eq(userId),
                    history.exerciseId.eq(exerciseId))
            .orderBy(history.score.desc())
            .limit(1)
            .fetchOne();

    }

    @Override
    public List<UsersExerciseSetHistory> findByUserIdAndExerciseDate(UUID userId, LocalDate exerciseDate) {
        QUsersExerciseSetHistory history = QUsersExerciseSetHistory.usersExerciseSetHistory;

        return queryFactory.selectFrom(history)
                           .where(
                               history.userId.eq(userId),
                               history.exerciseDate.year().eq(exerciseDate.getYear()),
                               history.exerciseDate.month().eq(exerciseDate.getMonthValue()),
                               history.exerciseDate.dayOfMonth().eq(exerciseDate.getDayOfMonth())
                           )
                           .fetch();
    }

    @Override
    public List<UsersExerciseSetHistory> findByUserIdAndYearAndWeek(UUID userId, int year, int week) {
        QUsersExerciseSetHistory history = QUsersExerciseSetHistory.usersExerciseSetHistory;

        return queryFactory.selectFrom(history)
                .where(
                        history.userId.eq(userId),
                        Expressions.numberTemplate(Integer.class, "YEAR({0})", history.exerciseDate).eq(year),
                        Expressions.numberTemplate(Integer.class, "WEEK({0})", history.exerciseDate).eq(week) // WEEK ISO 기준
                )
                .fetch();
    }
    
    @Override
    public List<UsersExerciseSetHistory> findByUserIdAndYearAndMonth(UUID userId, int year, int month) {
        QUsersExerciseSetHistory history = QUsersExerciseSetHistory.usersExerciseSetHistory;

        return queryFactory.selectFrom(history)
                .where(
                        history.userId.eq(userId),
                        history.exerciseDate.year().eq(year),
                        history.exerciseDate.month().eq(month))
                .fetch();
    }

    // 최근 n일치 ExerciseRecordGroupResponse 리스트 fetch ( 슬라이싱 )
    @Override
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
                        .where(setHistory.groupId.in(groupIds) // 가져온 groupId로 필터링
                                .and(setHistory.exerciseDate.after(LocalDateTime.now().minusDays(days)))) 
                .orderBy(setHistory.groupId.asc(), setHistory.exerciseDate.desc()) // 정렬
                .fetch();

        // 3. 결과 반환
        return new ExerciseRecordGroupListResponseWithHasNext(
                hasNext,
                results
        );
    }
    
    // 특정 month의 ExerciseRecordGroupResponse 리스트 fetch ( 슬라이싱 )
    @Override
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
                        .where(setHistory.groupId.in(groupIds) // 추출된 groupId로 필터링
                                        .and(setHistory.exerciseDate.between(startOfMonth, endOfMonth))) 
                .orderBy(setHistory.groupId.asc(), setHistory.exerciseDate.asc(), setHistory.set.asc()) // 정렬
                .fetch();
    
        // 3. 결과 반환
        return new ExerciseRecordGroupListResponseWithHasNext(
                hasNext,
                results
        );
    }

}
