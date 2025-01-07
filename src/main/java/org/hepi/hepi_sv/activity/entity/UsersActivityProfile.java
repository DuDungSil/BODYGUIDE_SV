package org.hepi.hepi_sv.activity.entity;

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

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "exercise_count")
    private int exerciseCount;

    @Column(name = "exercise_updated_at")
    private LocalDateTime exerciseUpdatedAt;

    @Column(name = "diet_count")
    private int dietCount;

    @Column(name = "diet_updated_at")
    private LocalDateTime dietUpdatedAt;

    @Column(name = "weight_count")
    private int weightCount;

    @Column(name = "weight_updated_at")
    private LocalDateTime weightUpdatedAt;

    public void updateExerciseCount() {
        this.exerciseCount++;
        this.exerciseUpdatedAt = LocalDateTime.now();
    }

    public void updateDietCount() {
        this.dietCount++;
        this.dietUpdatedAt = LocalDateTime.now();
    }

    public void updateWeightCount() {
        this.weightCount++;
        this.weightUpdatedAt = LocalDateTime.now();
    }

    // 생성 함수
    public static UsersActivityProfile create(UUID userId) {
        LocalDateTime now = LocalDateTime.now();
        return UsersActivityProfile.builder()
                .userId(userId)
                .exerciseCount(0)
                .exerciseUpdatedAt(now)
                .dietCount(0)
                .dietUpdatedAt(now)
                .weightCount(0)
                .weightUpdatedAt(now)
                .build();
    }

}
