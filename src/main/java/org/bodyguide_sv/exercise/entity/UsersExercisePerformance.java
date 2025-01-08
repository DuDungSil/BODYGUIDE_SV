package org.bodyguide_sv.exercise.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class UsersExercisePerformance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false) 
    private Long id;

    @Column(name = "total_score") 
    private double totalScore;

    @Column(name = "score_updated_at") 
    private LocalDateTime scoreUpdatedAt;

    @Column(name = "exercise_level") 
    private String exerciseLevel;

    @Column(name = "level_updated_at") 
    private LocalDateTime levelUpdatedAt;

    @Column(name = "big_three") 
    private Double bigThree;

    @Column(name = "big_three_updated_at") 
    private LocalDateTime bigThreeUpdatedAt;

}
