package org.hepi.hepi_sv.user.dto;

public record UserExerciseProfileRequest(
    Double benchWeight,
    int benchReps,
    Double squatWeight,
    int squatReps,
    Double deadWeight,
    int deadReps,
    Double overheadWeight,
    int overheadReps,
    int pushupReps,
    int pullupReps
) {
    
}
