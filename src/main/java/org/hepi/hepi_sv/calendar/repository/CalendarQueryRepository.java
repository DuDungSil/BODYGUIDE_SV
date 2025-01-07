package org.hepi.hepi_sv.calendar.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.hepi.hepi_sv.calendar.dto.CalendarMemoDTO;
import org.hepi.hepi_sv.calendar.entity.QUsersCalendarMemoHistory;
import org.hepi.hepi_sv.calendar.entity.QUsersIntakeHistory;
import org.hepi.hepi_sv.calendar.entity.QUsersWeightHistory;
import org.hepi.hepi_sv.exercise.entity.QUsersExerciseSetHistory;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CalendarQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<String> findMemoDays(UUID userId, String yyyymm) {
        QUsersCalendarMemoHistory qUsersCalendarMemoHistory = QUsersCalendarMemoHistory.usersCalendarMemoHistory;

        return queryFactory
                .select(Expressions.numberTemplate(Integer.class, "EXTRACT(DAY FROM {0})", qUsersCalendarMemoHistory.noteDate))
                .from(qUsersCalendarMemoHistory)
                .where(qUsersCalendarMemoHistory.userId.eq(userId)
                        .and(Expressions.stringTemplate(
                                "DATE_FORMAT({0}, '%Y%m')", qUsersCalendarMemoHistory.noteDate).eq(yyyymm))) // 년도와 월 비교
                .distinct()
                .fetch()
                .stream()
                .map(String::valueOf)
                .toList();
    }

    public List<String> findExerciseDays(UUID userId, String yyyymm) {
        QUsersExerciseSetHistory qUsersExerciseSetHistory = QUsersExerciseSetHistory.usersExerciseSetHistory;

        return queryFactory
                .select(Expressions.numberTemplate(Integer.class, "EXTRACT(DAY FROM {0})", qUsersExerciseSetHistory.exerciseDate))
                .from(qUsersExerciseSetHistory)
                .where(qUsersExerciseSetHistory.userId.eq(userId)
                        .and(Expressions.stringTemplate(
                                "DATE_FORMAT({0}, '%Y%m')", qUsersExerciseSetHistory.exerciseDate).eq(yyyymm))) // 년도와 월 비교
                .distinct()
                .fetch()
                .stream()
                .map(String::valueOf)
                .toList();
    }

    public List<String> findNutritionDays(UUID userId, String yyyymm) {
        QUsersIntakeHistory qUsersIntakeHistory = QUsersIntakeHistory.usersIntakeHistory;

        return queryFactory
                .select(Expressions.numberTemplate(Integer.class, "EXTRACT(DAY FROM {0})", qUsersIntakeHistory.intakeDate))
                .from(qUsersIntakeHistory)
                .where(qUsersIntakeHistory.userId.eq(userId)
                        .and(Expressions.stringTemplate(
                                "DATE_FORMAT({0}, '%Y%m')", qUsersIntakeHistory.intakeDate).eq(yyyymm))) // 년도와 월 비교
                .distinct()
                .fetch()
                .stream()
                .map(String::valueOf)
                .toList();
    }

    public List<String> findWeightDays(UUID userId, String yyyymm) {
        QUsersWeightHistory qUsersWeightHistory = QUsersWeightHistory.usersWeightHistory;

        return queryFactory
                .select(Expressions.numberTemplate(Integer.class, "EXTRACT(DAY FROM {0})", qUsersWeightHistory.recordDate))
                .from(qUsersWeightHistory)
                .where(qUsersWeightHistory.userId.eq(userId)
                        .and(Expressions.stringTemplate(
                                "DATE_FORMAT({0}, '%Y%m')", qUsersWeightHistory.recordDate).eq(yyyymm))) // 년도와 월 비교
                .distinct()
                .fetch()
                .stream()
                .map(String::valueOf)
                .toList();
    }

    public CalendarMemoDTO findCalendarDataBySelectedDate(UUID userId, String yyyymmdd) {
        QUsersCalendarMemoHistory qUsersCalendarMemoHistory = QUsersCalendarMemoHistory.usersCalendarMemoHistory;
        QUsersExerciseSetHistory qUsersExerciseSetHistory = QUsersExerciseSetHistory.usersExerciseSetHistory;
        QUsersIntakeHistory qUsersIntakeHistory = QUsersIntakeHistory.usersIntakeHistory;
        QUsersWeightHistory qUsersWeightHistory = QUsersWeightHistory.usersWeightHistory;

        // LocalDate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate selectedDate = LocalDate.parse(yyyymmdd, formatter);

        // 메모 조회
        String note = queryFactory.select(qUsersCalendarMemoHistory.note)
                .from(qUsersCalendarMemoHistory)
                .where(qUsersCalendarMemoHistory.userId.eq(userId)
                        .and(qUsersCalendarMemoHistory.noteDate.eq(selectedDate)))
                .fetchOne();

        // 운동 기록 합계 조회
        Double exercise = queryFactory.select(qUsersExerciseSetHistory.weight.sum().coalesce(0.0))
                .from(qUsersExerciseSetHistory)
                .where(qUsersExerciseSetHistory.userId.eq(userId)
                        .and(qUsersExerciseSetHistory.exerciseDate.eq(selectedDate)))
                .fetchOne();

        // 식사 기록 합계 조회
        Double intake = queryFactory.select(qUsersIntakeHistory.calory.sum().coalesce(0.0))
                .from(qUsersIntakeHistory)
                .where(qUsersIntakeHistory.userId.eq(userId)
                        .and(qUsersIntakeHistory.intakeDate.eq(selectedDate)))
                .fetchOne();

        // 체중 기록 조회
        Double weight = queryFactory.select(qUsersWeightHistory.weight.coalesce(0.0))
                .from(qUsersWeightHistory)
                .where(qUsersWeightHistory.userId.eq(userId)
                        .and(qUsersWeightHistory.recordDate.eq(selectedDate)))
                .fetchOne();

        // DTO로 반환
        return new CalendarMemoDTO(selectedDate, note, exercise, intake, weight);
    }


}