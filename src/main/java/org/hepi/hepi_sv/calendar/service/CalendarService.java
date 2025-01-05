package org.hepi.hepi_sv.calendar.service;

import java.util.List;
import java.util.UUID;

import org.hepi.hepi_sv.calendar.dto.CalendarMemoDTO;
import org.hepi.hepi_sv.calendar.entity.UsersCalendarMemoHistory;
import org.hepi.hepi_sv.calendar.repository.CalendarQueryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CalendarService {

    private final CalendarQueryRepository calendarQueryRepository;

    public List<String> getMemoDays(UUID userId, String yyyymm) {
        List<String> list = calendarQueryRepository.findMemoDays(userId, yyyymm);
        return list;
    }

    public List<String> getExerciseDays(UUID userId, String yyyymm) {
        List<String> list = calendarQueryRepository.findExerciseDays(userId, yyyymm);
        return list;
    }

    public List<String> getNutritionDays(UUID userId, String yyyymm) {
        List<String> list = calendarQueryRepository.findNutritionDays(userId, yyyymm);
        return list;
    }

    public List<String> getWeightDays(UUID userId, String yyyymm) {
        List<String> list = calendarQueryRepository.findWeightDays(userId, yyyymm);
        return list;
    }

    public CalendarMemoDTO getCalendarMemoDetail(UUID userId, String yyyymmdd) {
        CalendarMemoDTO detailDTO = calendarQueryRepository.findCalendarDataBySelectedDate(userId, yyyymmdd);
        return detailDTO;
    }

}
