package org.bodyguide_sv.exercise.dto;

public record ExerciseData(
    int exerId,
    String exerName,
    String exerNameKr,
    int exerType,
    int muscleId,
    int thresholdType
) {
    
}
