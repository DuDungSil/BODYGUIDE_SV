package org.bodyguide_sv.exercise.dto;

public record UpdatedMuscleScoreDTO(
    double coreScore,
    double lowerBodyScore,
    double backScore,
    double chestScore,
    double shoulderScore,
    double armScore             
) {
    
}
