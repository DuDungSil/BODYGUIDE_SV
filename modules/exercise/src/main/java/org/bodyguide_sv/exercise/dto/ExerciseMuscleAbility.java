package org.bodyguide_sv.exercise.dto;

public record ExerciseMuscleAbility(
    ExerciseAnalysisProfile core,
    ExerciseAnalysisProfile lowerBody,
    ExerciseAnalysisProfile back,
    ExerciseAnalysisProfile chest,
    ExerciseAnalysisProfile shoulder,
    ExerciseAnalysisProfile arm
) {
    
}
