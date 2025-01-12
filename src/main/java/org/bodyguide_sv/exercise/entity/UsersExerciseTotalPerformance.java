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
@Table(name = "USERS_EXERCISE_TOTAL_PERFORMANCE")
public class UsersExerciseTotalPerformance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false) 
    private Long id;

    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "total_score") 
    private double totalScore;

    @Column(name = "score_updated_at") 
    private LocalDateTime scoreUpdatedAt;

    @Column(name = "exercise_level") 
    private int exerciseLevel;

    @Column(name = "level_updated_at") 
    private LocalDateTime levelUpdatedAt;

    @Column(name = "big_three") 
    private Double bigThree;

    @Column(name = "big_three_updated_at") 
    private LocalDateTime bigThreeUpdatedAt;

    public void updateTotalScore(double newTotalScore) {
        if (Double.compare(this.totalScore, newTotalScore) != 0) { // 값이 다를 경우만 업데이트
            this.totalScore = newTotalScore;
            this.scoreUpdatedAt = LocalDateTime.now();
        }
    }

    public void updateExerciseLevel(int newExerciseLevel) {
        if (this.exerciseLevel != newExerciseLevel) { // 값이 다를 경우만 업데이트
            this.exerciseLevel = newExerciseLevel;
            this.levelUpdatedAt = LocalDateTime.now();
        }
    }

    public void updateBigThree(Double newBigThree) {
        if (this.bigThree == null || !this.bigThree.equals(newBigThree)) { // 값이 다를 경우만 업데이트
            this.bigThree = newBigThree;
            this.bigThreeUpdatedAt = LocalDateTime.now();
        }
    }


    public static UsersExerciseTotalPerformance createNewPerformance(UUID userId) {
        return UsersExerciseTotalPerformance.builder()
                .userId(userId)
                .totalScore(0.0)
                .scoreUpdatedAt(LocalDateTime.now())
                .exerciseLevel(1)
                .levelUpdatedAt(LocalDateTime.now())
                .bigThree(0.0)
                .bigThreeUpdatedAt(LocalDateTime.now())
                .build();
    }

}
