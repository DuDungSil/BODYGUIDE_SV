package org.hepi.hepi_sv.exercise.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hepi.hepi_sv.exercise.vo.ExerciseStats;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
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
@Table(name = "USERS_EXERCISE_PROFILE")
public class UsersExerciseStats {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Embedded
    private ExerciseStats exerciseStats; // 운동 데이터

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateWithStats(ExerciseStats requestStats) {
        this.exerciseStats.updateWith(requestStats); // 운동 데이터 업데이트
        this.updatedAt = LocalDateTime.now();       // 업데이트 시간 갱신
    }

}
