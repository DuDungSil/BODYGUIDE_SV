package org.hepi.hepi_sv.calender.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class CalendarMemoDTO {
    private Long historyId;  // 기록 ID
    private UUID userId;     // 사용자 ID
    private String recordAt; // 기록 시간
    private String note;     // 메모 내용
    private LocalDate noteDate; // 메모 날짜


    // 생성자
    public CalendarMemoDTO(Long historyId, UUID userId, String recordAt, String note, LocalDate noteDate) {
        this.historyId = historyId;
        this.userId = userId;
        this.recordAt = recordAt;
        this.note = note;
        this.noteDate = noteDate;
    }
}
