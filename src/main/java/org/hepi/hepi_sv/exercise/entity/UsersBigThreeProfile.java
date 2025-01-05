package org.hepi.hepi_sv.exercise.entity;

import org.hepi.hepi_sv.exercise.vo.BigThreeProfile;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UsersBigThreeProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "score", column = @Column(name = "squat_score")),
        @AttributeOverride(name = "weight", column = @Column(name = "squat_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "squat_reps")),
        @AttributeOverride(name = "strength", column = @Column(name = "squat_strength")),
        @AttributeOverride(name = "lastUpdated", column = @Column(name = "squat_last_updated"))
    })
    private BigThreeProfile squat;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "score", column = @Column(name = "deadlift_score")),
        @AttributeOverride(name = "weight", column = @Column(name = "deadlift_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "deadlift_reps")),
        @AttributeOverride(name = "strength", column = @Column(name = "deadlift_strength")),
        @AttributeOverride(name = "lastUpdated", column = @Column(name = "deadlift_last_updated"))
    })
    private BigThreeProfile deadlift;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "score", column = @Column(name = "benchpress_score")),
        @AttributeOverride(name = "weight", column = @Column(name = "benchpress_weight")),
        @AttributeOverride(name = "reps", column = @Column(name = "benchpress_reps")),
        @AttributeOverride(name = "strength", column = @Column(name = "benchpress_strength")),
        @AttributeOverride(name = "lastUpdated", column = @Column(name = "benchpress_last_updated"))
    })
    private BigThreeProfile benchPress;



}