package org.bodyguide_sv.exercise.repository.custom.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.dto.MaxWeightAndRepsDTO;
import org.bodyguide_sv.exercise.entity.QUsersExerciseSetHistory;
import org.bodyguide_sv.exercise.entity.UsersExerciseSetHistory;
import org.bodyguide_sv.exercise.repository.custom.UsersExerciseSetHistoryCustomRepository;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UsersExerciseSetHistoryCustomRepositoryImpl implements UsersExerciseSetHistoryCustomRepository {
    
    private final JPAQueryFactory queryFactory;

    public UsersExerciseSetHistoryCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public MaxWeightAndRepsDTO findMaxWeightAndRepsByUserIdAndExerciseId(UUID userId, int exerciseId) {
        QUsersExerciseSetHistory history = QUsersExerciseSetHistory.usersExerciseSetHistory;

        return queryFactory.select(
                                Projections.constructor(MaxWeightAndRepsDTO.class, history.weight, history.reps)
                            )
                           .from(history)
                           .where(
                               history.userId.eq(userId),
                               history.exerciseId.eq(exerciseId)
                           )
                           .orderBy(history.weight.desc(), history.reps.desc())
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
                               history.exerciseDate.month().eq(month)
                           )
                           .fetch();
    }

}
