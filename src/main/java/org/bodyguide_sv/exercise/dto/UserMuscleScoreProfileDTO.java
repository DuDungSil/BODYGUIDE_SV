package org.bodyguide_sv.exercise.dto;

public record UserMuscleScoreProfileDTO(
    MuscleScore core,
    MuscleScore lowerBody,
    MuscleScore back,
    MuscleScore chest,
    MuscleScore shoulder,
    MuscleScore arm
) {
    public static record MuscleScore(
        Integer exerciseId,
        Double weight,
        Integer reps,
        Double score
    ) {
        
    }
}
