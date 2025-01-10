package org.bodyguide_sv.exercise.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.exercise.vo.BigThreeProfile;

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
@Table(name = "USERS_BIGTHREE_PROFILE")
public class UsersBigThreeProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private UUID userId;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "score", column = @Column(name = "squat_score")),
        @AttributeOverride(name = "weight", column = @Column(name = "squat_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "squat_reps")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "squat_updated_at"))
    })
    private BigThreeProfile squat;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "score", column = @Column(name = "deadlift_score")),
        @AttributeOverride(name = "weight", column = @Column(name = "deadlift_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "deadlift_reps")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "deadlift_updated_at"))
    })
    private BigThreeProfile deadlift;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "score", column = @Column(name = "benchpress_score")),
        @AttributeOverride(name = "weight", column = @Column(name = "benchpress_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "benchpress_reps")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "benchpress_updated_at"))
    })
    private BigThreeProfile benchPress;

    public void updateSquat(Double weight, Integer reps, Double score) {
        if (squat == null) {
            squat = BigThreeProfile.createDefault();
        }
        squat.setWeight(weight);
        squat.setReps(reps);
        squat.setScore(score);
        squat.setUpdatedAt(LocalDateTime.now());
    }

    public void updateDeadlift(Double weight, Integer reps, Double score) {
        if (deadlift == null) {
            deadlift = BigThreeProfile.createDefault();
        }
        deadlift.setWeight(weight);
        deadlift.setReps(reps);
        deadlift.setScore(score);
        deadlift.setUpdatedAt(LocalDateTime.now());
    }

    public void updateBenchPress(Double weight, Integer reps, Double score) {
        if (benchPress == null) {
            benchPress = BigThreeProfile.createDefault();
        }
        benchPress.setWeight(weight);
        benchPress.setReps(reps);
        benchPress.setScore(score);
        benchPress.setUpdatedAt(LocalDateTime.now());
    }

    public static UsersBigThreeProfile createDefaultProfile(UUID userId) {
        return UsersBigThreeProfile.builder()
            .userId(userId)
            .squat(BigThreeProfile.createDefault())
            .deadlift(BigThreeProfile.createDefault())
            .benchPress(BigThreeProfile.createDefault())
            .build();
    }

}