package org.bodyguide_sv.exercise.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS_EXERCISE_BEST_SCORE")
public class UsersExerciseBestScore {
    
    @EmbeddedId
    private UsersExerciseBestScoreId id;

    @Column(name = "weight") 
    private Double weight;

    @Column(name = "reps") 
    private int reps;

    @Column(name = "weight_updated_at") 
    private LocalDateTime weightUpdatedAt;

    @Column(name = "score_weight") 
    private Double scoreWeight;

    @Column(name = "score_reps") 
    private int scoreReps;

    @Column(name = "score") 
    private Double score;

    @Column(name = "score_updated_at") 
    private LocalDateTime scoreUpdatedAt;

    public static UsersExerciseBestScore createNew(UUID userId, int exerciseId) {
        return UsersExerciseBestScore.builder()
            .id(new UsersExerciseBestScoreId(userId, exerciseId))
            .weight(0.0)
            .reps(0)    
            .weightUpdatedAt(LocalDateTime.now())
            .scoreWeight(0.0)
            .scoreReps(0)
            .score(0.0) 
            .scoreUpdatedAt(LocalDateTime.now())
            .build();
    }

    public void updateWeightAndReps(Double weight, int reps) {
        this.weight = weight;
        this.reps = reps;
        this.weightUpdatedAt = LocalDateTime.now();
    }

    public void updateScore(Double scoreWeight, int scoreReps, Double score) {
        this.scoreWeight = scoreWeight;
        this.scoreReps = scoreReps;
        this.score = score;
        this.scoreUpdatedAt = LocalDateTime.now();
    }

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    public static class UsersExerciseBestScoreId implements Serializable {
        
        @Column(name = "user_id", nullable = false, updatable = false)
        private UUID userId;

        @Column(name = "exercise_id", nullable = false, updatable = false, columnDefinition = "SMALLINT")
        private int exerciseId;

    }

}
