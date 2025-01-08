package org.bodyguide_sv.calendar.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS_WEIGHT_HISTORY")
public class UsersWeightHistory {
    
    @Id
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "record_at")
    private LocalDateTime recordAt;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "record_date")
    private LocalDate recordDate;

}
