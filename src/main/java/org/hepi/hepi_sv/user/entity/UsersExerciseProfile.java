package org.hepi.hepi_sv.user.entity;

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
@Table(name = "USERS_EXERCISE_PROFILE")
public class UsersExerciseProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", updatable = false, nullable = false) 
    private Long id;

    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "bench_weight")
    private Double benchWeight;

    @Column(name = "bench_reps")
    private Integer benchReps;

    @Column(name = "squat_weight")
    private Double squatWeight;

    @Column(name = "squat_reps")
    private Integer squatReps;

    @Column(name = "dead_weight")
    private Double deadWeight;

    @Column(name = "dead_reps")
    private Integer deadReps;

    @Column(name = "overhead_weight")
    private Double overheadWeight;

    @Column(name = "overhead_reps")
    private Integer overheadReps;

    @Column(name = "pushup_reps")
    private Integer pushupReps;

    @Column(name = "pullup_reps")
    private Integer pullupReps;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
