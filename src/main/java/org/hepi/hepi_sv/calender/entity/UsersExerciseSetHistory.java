package org.hepi.hepi_sv.calender.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "USERS_EXERCISE_SET_HISTORY")
public class UsersExerciseSetHistory {

    @Id
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "record_at")
    private String recordAt;

    @Column(name = "exercise_name")
    private String exerciseName;

    @Column(name = "set")
    private String set;

    @Column(name = "weight")
    private String weight;

    @Column(name = "reps")
    private String reps;

    @Column(name = "exercise_date")
    private LocalDate exerciseDate;
}
