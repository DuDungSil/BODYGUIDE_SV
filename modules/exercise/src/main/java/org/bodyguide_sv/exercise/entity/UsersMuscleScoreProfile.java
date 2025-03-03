package org.bodyguide_sv.exercise.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.exercise.vo.MuscleScoreProfile;

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
@Table(name = "USERS_MUSCLE_SCORE_PROFILE")
public class UsersMuscleScoreProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private UUID userId;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "core_exercise_id", columnDefinition = "SMALLINT")),
        @AttributeOverride(name = "weight", column = @Column(name = "core_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "core_reps")),
        @AttributeOverride(name = "score", column = @Column(name = "core_score")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "core_updated_at"))
    })
    private MuscleScoreProfile core;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "lower_body_exercise_id", columnDefinition = "SMALLINT")),
        @AttributeOverride(name = "weight", column = @Column(name = "lower_body_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "lower_body_reps")),
        @AttributeOverride(name = "score", column = @Column(name = "lower_body_score")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "lower_body_updated_at"))
    })
    private MuscleScoreProfile lowerBody;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "back_exercise_id", columnDefinition = "SMALLINT")),
        @AttributeOverride(name = "weight", column = @Column(name = "back_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "back_reps")),
        @AttributeOverride(name = "score", column = @Column(name = "back_score")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "back_updated_at"))
    })
    private MuscleScoreProfile back;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "chest_exercise_id", columnDefinition = "SMALLINT")),
        @AttributeOverride(name = "weight", column = @Column(name = "chest_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "chest_reps")),
        @AttributeOverride(name = "score", column = @Column(name = "chest_score")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "chest_updated_at"))
    })
    private MuscleScoreProfile chest;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "shoulder_exercise_id", columnDefinition = "SMALLINT")),
        @AttributeOverride(name = "weight", column = @Column(name = "shoulder_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "shoulder_reps")),
        @AttributeOverride(name = "score", column = @Column(name = "shoulder_score")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "shoulder_updated_at"))
    })
    private MuscleScoreProfile shoulder;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "arm_exercise_id", columnDefinition = "SMALLINT")),
        @AttributeOverride(name = "weight", column = @Column(name = "arm_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "arm_reps")),
        @AttributeOverride(name = "score", column = @Column(name = "arm_score")),
        @AttributeOverride(name = "updatedAt", column = @Column(name = "arm_last_updated"))
    })
    private MuscleScoreProfile arm;

    // 업데이트 로직
    public void updateCore(int exerciseId, double weight, int reps, double score) {
        if (core == null) {
            core = MuscleScoreProfile.createDefault();
        }
        core.setExerciseId(exerciseId);
        core.setWeight(weight);
        core.setReps(reps);
        core.setScore(score);
        core.setUpdatedAt(LocalDateTime.now());
    }

    public void updateLowerBody(int exerciseId, double weight, int reps, double score) {
        if (lowerBody == null) {
            lowerBody = MuscleScoreProfile.createDefault();
        }
        lowerBody.setExerciseId(exerciseId);
        lowerBody.setWeight(weight);
        lowerBody.setReps(reps);
        lowerBody.setScore(score);
        lowerBody.setUpdatedAt(LocalDateTime.now());
    }

    public void updateBack(int exerciseId, double weight, int reps, double score) {
        if (back == null) {
            back = MuscleScoreProfile.createDefault();
        }
        back.setExerciseId(exerciseId);
        back.setWeight(weight);
        back.setReps(reps);
        back.setScore(score);
        back.setUpdatedAt(LocalDateTime.now());
    }

    public void updateChest(int exerciseId, double weight, int reps, double score) {
        if (chest == null) {
            chest = MuscleScoreProfile.createDefault();
        }
        chest.setExerciseId(exerciseId);
        chest.setWeight(weight);
        chest.setReps(reps);
        chest.setScore(score);
        chest.setUpdatedAt(LocalDateTime.now());
    }

    public void updateShoulder(int exerciseId, double weight, int reps, double score) {
        if (shoulder == null) {
            shoulder = MuscleScoreProfile.createDefault();
        }
        shoulder.setExerciseId(exerciseId);
        shoulder.setWeight(weight);
        shoulder.setReps(reps);
        shoulder.setScore(score);
        shoulder.setUpdatedAt(LocalDateTime.now());
    }

    public void updateArm(int exerciseId, double weight, int reps, double score) {
        if (arm == null) {
            arm = MuscleScoreProfile.createDefault();
        }
        arm.setExerciseId(exerciseId);
        arm.setWeight(weight);
        arm.setReps(reps);
        arm.setScore(score);
        arm.setUpdatedAt(LocalDateTime.now());
    }

    // 디폴트 프로필 생성 로직
    public static UsersMuscleScoreProfile createDefaultProfile(UUID userId) {
        return UsersMuscleScoreProfile.builder()
                .userId(userId)
                .core(MuscleScoreProfile.createDefault())
                .lowerBody(MuscleScoreProfile.createDefault())
                .back(MuscleScoreProfile.createDefault())
                .chest(MuscleScoreProfile.createDefault())
                .shoulder(MuscleScoreProfile.createDefault())
                .arm(MuscleScoreProfile.createDefault())
                .build();
    }

}
