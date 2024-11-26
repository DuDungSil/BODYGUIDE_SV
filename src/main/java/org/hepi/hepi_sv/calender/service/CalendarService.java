package org.hepi.hepi_sv.calender.service;

import java.util.List;
import java.util.UUID;

import org.hepi.hepi_sv.calender.dto.CalendarMemoDTO;
import org.hepi.hepi_sv.calender.entity.UsersCalendarMemoHistory;
import org.hepi.hepi_sv.calender.resitory.CalendarQueryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class CalendarService {

    private final CalendarQueryRepository calendarQueryRepository;

    public List<String> getMemoDays(UUID userId) {
        List<String> list = calendarQueryRepository.findMemoDays(userId);
        return list;
    }

    public List<String> getExerciseDays(UUID userId) {
        List<String> list = calendarQueryRepository.findExerciseDays(userId);
        return list;
    }

    public List<String> getNutritionDays(UUID userId) {
        List<String> list = calendarQueryRepository.findNutritionDays(userId);
        return list;
    }

    public CalendarMemoDTO getCalendarMemoDetail(UUID userId, String number) {
        UsersCalendarMemoHistory usersCalendarMemoHistory = calendarQueryRepository.findCalendarMemoDetail(userId, number);
        return new CalendarMemoDTO(
                usersCalendarMemoHistory.getHistoryId(),
                usersCalendarMemoHistory.getUserId(),
                usersCalendarMemoHistory.getRecordAt(),
                usersCalendarMemoHistory.getNote(),
                usersCalendarMemoHistory.getNoteDate()
        );
    }
}
