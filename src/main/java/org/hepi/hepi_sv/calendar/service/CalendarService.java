package org.hepi.hepi_sv.calendar.service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.hepi.hepi_sv.calendar.dto.CalendarMemoDTO;
import org.hepi.hepi_sv.calendar.repository.CalendarQueryRepository;
import org.hepi.hepi_sv.recommend.dto.RecommendSupplementRequest;
import org.hepi.hepi_sv.recommend.dto.RecommendSupplementResponse;
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

    public CalendarMemoDTO postMemo(UUID userId, CalendarMemoDTO memoRequest) {
        // yyyymm 생성 (LocalDate의 연도와 월 추출)
        String yyyymmdd = memoRequest.getSelectedDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // 메모 날짜 리스트 조회
        List<String> memoDays = calendarQueryRepository.findMemoDays(userId, yyyymmdd.substring(0, 6));

        // 메모가 존재하는지 확인
        boolean memoExists = memoDays.contains(String.valueOf(memoRequest.getSelectedDate().getDayOfMonth()));

        if (memoExists) {
            // 메모가 존재하면 업데이트
            calendarQueryRepository.updateMemo(userId, memoRequest);
        } else {
            // 메모가 없으면 새로 생성
            calendarQueryRepository.createMemo(userId, memoRequest);
        }

        // 최종 저장된 메모 반환
        return calendarQueryRepository.findCalendarDataBySelectedDate(userId, yyyymmdd);
    }
}
