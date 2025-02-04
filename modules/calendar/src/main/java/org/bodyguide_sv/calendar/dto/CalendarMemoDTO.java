package org.bodyguide_sv.calendar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarMemoDTO {
    private LocalDate selectedDate; // 선택 날짜
    private String note; // 메모 내용
    private Double exercise; // 운동 기록
    private Double intake; // 식사 기록
    private Double weight; // 체중 기록
}
