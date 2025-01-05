package org.hepi.hepi_sv.activity.entity;

import java.time.LocalDateTime;

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

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS_ACTIVITY_PROFILE")
public class UsersActivityProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "exercise_count")
    private int exerciseCount;

    @Column(name = "last_exercise_time")
    private LocalDateTime lastExerciseTime;

    @Column(name = "diet_count")
    private int dietCount;

    @Column(name = "last_diet_time")
    private LocalDateTime lastDietTime;

    @Column(name = "weight_count")
    private int weightCount;

    @Column(name = "last_weight_time")
    private LocalDateTime lastWeightTime;

    public void updateExerciseCount() {
        this.exerciseCount++;
        this.lastExerciseTime = LocalDateTime.now();
    }

    public void updateDietCount() {
        this.dietCount++;
        this.lastDietTime = LocalDateTime.now();
    }

    public void updateWeightCount() {
        this.weightCount++;
        this.lastWeightTime = LocalDateTime.now();
    }

}
