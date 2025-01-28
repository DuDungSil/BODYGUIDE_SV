package org.bodyguide_sv.exercise.dto;

import org.bodyguide_sv.exercise.enums.MuscleGroupType;

public record MuscleGroupScoreDto(
    MuscleGroupType muscleGroupType,
    int exerciseId,
    double weight,
    int reps,
    double maxScore
) {

    
}