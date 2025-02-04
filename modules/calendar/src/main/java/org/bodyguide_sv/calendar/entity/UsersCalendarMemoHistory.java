package org.bodyguide_sv.calendar.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "USERS_CALENDAR_MEMO_HISTORY")
public class UsersCalendarMemoHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
