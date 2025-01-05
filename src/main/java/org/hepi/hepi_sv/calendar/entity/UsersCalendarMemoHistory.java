package org.hepi.hepi_sv.calendar.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USERS_CALENDAR_MEMO_HISTORY")
public class UsersCalendarMemoHistory {

    @Id
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "record_at")
    private LocalDateTime recordAt;

    @Column(name = "note")
    private String note;

    @Column(name = "note_date")
    private LocalDate noteDate;
}
