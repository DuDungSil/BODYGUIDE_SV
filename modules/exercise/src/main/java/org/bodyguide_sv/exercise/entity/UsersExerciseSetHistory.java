package org.bodyguide_sv.exercise.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS_EXERCISE_SET_HISTORY")
public class UsersExerciseSetHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", updatable = false, nullable = false)
    private Long historyId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "group_id", columnDefinition = "SMALLINT")
    private int groupId;

    @Column(name = "exercise_date")
    private LocalDateTime exerciseDate;

    @Column(name = "exercise_id", columnDefinition = "SMALLINT")
    private Integer exerciseId;

    @Column(name = "set_number")
    private Integer set;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "reps")
    private Integer reps;

    @Column(name = "score")
    private Double score;

    @Column(name = "strength")
    private Double strength;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
}
