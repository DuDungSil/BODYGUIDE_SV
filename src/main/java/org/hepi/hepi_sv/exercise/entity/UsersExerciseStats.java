package org.hepi.hepi_sv.exercise.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hepi.hepi_sv.exercise.vo.ExerciseStats;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
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
@Table(name = "USERS_EXERCISE_STATS")
public class UsersExerciseStats {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;


    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "benchWeight", column = @Column(name = "benchpress_weight")),
        @AttributeOverride(name = "benchReps", column = @Column(name = "benchpress_reps")),
        @AttributeOverride(name = "squatWeight", column = @Column(name = "squat_weight")),
        @AttributeOverride(name = "squatReps", column = @Column(name = "squat_reps")),
        @AttributeOverride(name = "deadWeight", column = @Column(name = "deadlift_weight")),
        @AttributeOverride(name = "deadReps", column = @Column(name = "deadlift_reps")),
        @AttributeOverride(name = "overheadWeight", column = @Column(name = "overheadpress_weight")),
        @AttributeOverride(name = "overheadReps", column = @Column(name = "overheadpress_reps")),
        @AttributeOverride(name = "pushupReps", column = @Column(name = "pushup_reps")),
        @AttributeOverride(name = "pullupReps", column = @Column(name = "pullup_reps"))
    })
    private ExerciseStats exerciseStats; // 운동 데이터

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateWithStats(ExerciseStats requestStats) {
        this.exerciseStats.updateWith(requestStats); // 운동 데이터 업데이트
        this.updatedAt = LocalDateTime.now(); // 업데이트 시간 갱신
    }

        // 생성 메서드
        public static UsersExerciseStats create(UUID userId) {
            return UsersExerciseStats.builder()
                    .userId(userId)
                    .exerciseStats(ExerciseStats.createDefault()) // 기본값 설정
                    .updatedAt(LocalDateTime.now()) // 생성 시점의 시간
                    .build();
        }

}
