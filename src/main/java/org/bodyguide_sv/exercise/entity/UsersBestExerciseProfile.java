package org.bodyguide_sv.exercise.entity;

import org.bodyguide_sv.exercise.vo.BestExerciseProfile;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UsersBestExerciseProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "core_exercise_id")),
        @AttributeOverride(name = "score", column = @Column(name = "core_score")),
        @AttributeOverride(name = "strength", column = @Column(name = "core_strength")),
        @AttributeOverride(name = "lastUpdated", column = @Column(name = "core_last_updated"))
    })
    private BestExerciseProfile core;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "lower_body_exercise_id")),
        @AttributeOverride(name = "score", column = @Column(name = "lower_body_score")),
        @AttributeOverride(name = "strength", column = @Column(name = "lower_body_strength")),
        @AttributeOverride(name = "lastUpdated", column = @Column(name = "lower_body_last_updated"))
    })
    private BestExerciseProfile lowerBody;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "back_exercise_id")),
        @AttributeOverride(name = "score", column = @Column(name = "back_score")),
        @AttributeOverride(name = "strength", column = @Column(name = "back_strength")),
        @AttributeOverride(name = "lastUpdated", column = @Column(name = "back_last_updated"))
    })
    private BestExerciseProfile back;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "chest_exercise_id")),
        @AttributeOverride(name = "score", column = @Column(name = "chest_score")),
        @AttributeOverride(name = "strength", column = @Column(name = "chest_strength")),
        @AttributeOverride(name = "lastUpdated", column = @Column(name = "chest_last_updated"))
    })
    private BestExerciseProfile chest;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "shoulder_exercise_id")),
        @AttributeOverride(name = "score", column = @Column(name = "shoulder_score")),
        @AttributeOverride(name = "strength", column = @Column(name = "shoulder_strength")),
        @AttributeOverride(name = "lastUpdated", column = @Column(name = "shoulder_last_updated"))
    })
    private BestExerciseProfile shoulder;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "exerciseId", column = @Column(name = "arm_exercise_id")),
        @AttributeOverride(name = "score", column = @Column(name = "arm_score")),
        @AttributeOverride(name = "strength", column = @Column(name = "arm_strength")),
        @AttributeOverride(name = "lastUpdated", column = @Column(name = "arm_last_updated"))
    })
    private BestExerciseProfile arm;
    
}
