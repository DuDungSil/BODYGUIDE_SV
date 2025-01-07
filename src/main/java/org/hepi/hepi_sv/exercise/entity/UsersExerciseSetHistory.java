package org.hepi.hepi_sv.exercise.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS_EXERCISE_SET_HISTORY")
public class UsersExerciseSetHistory {

    @Id
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "record_at")
    private LocalDateTime recordAt;

    @Column(name = "exercise_id", columnDefinition = "SMALLINT")
    private Integer exerciseId;

    @Column(name = "set")
    private Integer set;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "reps")
    private Integer reps;

    @Column(name = "exercise_date")
    private LocalDate exerciseDate;
}
