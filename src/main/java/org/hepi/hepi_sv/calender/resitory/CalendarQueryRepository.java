package org.hepi.hepi_sv.calender.resitory;

import java.util.List;
import java.util.UUID;

import org.hepi.hepi_sv.calender.entity.QUsersCalendarMemoHistory;
import org.hepi.hepi_sv.calender.entity.QUsersExerciseSetHistory;
import org.hepi.hepi_sv.calender.entity.QUsersIntakeHistory;
import org.hepi.hepi_sv.calender.entity.UsersCalendarMemoHistory;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CalendarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<String> findMemoDays(UUID userId) {
        QUsersCalendarMemoHistory qUsersCalendarMemoHistory = QUsersCalendarMemoHistory.usersCalendarMemoHistory;

        return queryFactory
                .select(Expressions.stringTemplate("extract(DAY FROM {0})", qUsersCalendarMemoHistory.noteDate))
                .from(qUsersCalendarMemoHistory)
                .where(qUsersCalendarMemoHistory.userId.eq(userId))
                .fetch();
    }

    public List<String> findExerciseDays(UUID userId) {
        QUsersExerciseSetHistory qUsersExerciseSetHistory = QUsersExerciseSetHistory.usersExerciseSetHistory;

        return queryFactory
                .select(Expressions.stringTemplate("extract(DAY FROM {0})", qUsersExerciseSetHistory.exerciseDate))
                .from(qUsersExerciseSetHistory)
                .where(qUsersExerciseSetHistory.userId.eq(userId))
                .fetch();
    }

    public List<String> findNutritionDays(UUID userId) {
        QUsersIntakeHistory qUsersIntakeHistory = QUsersIntakeHistory.usersIntakeHistory;

        return queryFactory
                .select(Expressions.stringTemplate("extract(DAY FROM {0})", qUsersIntakeHistory.intakeDate))
                .from(qUsersIntakeHistory)
                .where(qUsersIntakeHistory.userId.eq(userId))
                .fetch();
    }


    public UsersCalendarMemoHistory findCalendarMemoDetail(UUID userId, String number) {
        QUsersCalendarMemoHistory qUsersCalendarMemoHistory = QUsersCalendarMemoHistory.usersCalendarMemoHistory;

        return queryFactory.selectFrom(qUsersCalendarMemoHistory)
                .where(qUsersCalendarMemoHistory.userId.eq(userId)
                        .and(Expressions.stringTemplate("extract(DAY FROM {0})", qUsersCalendarMemoHistory.noteDate)
                                .eq(number))
                )
                .fetchOne();
    }


}