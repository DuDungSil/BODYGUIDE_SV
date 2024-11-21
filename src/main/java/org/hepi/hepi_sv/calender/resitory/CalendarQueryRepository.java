package org.hepi.hepi_sv.calender.resitory;

import java.util.List;

import org.hepi.hepi_sv.calender.entity.QUsersCalendarMemoHistory;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CalendarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<String> findMemoDays() {
        QUsersCalendarMemoHistory exercisePurpose = QUsersCalendarMemoHistory.usersCalendarMemoHistory;

        return queryFactory
                .select(Expressions.stringTemplate("extract(DAY FROM {0})", exercisePurpose.noteDate))
                .from(exercisePurpose)
                .fetch();
    }
}