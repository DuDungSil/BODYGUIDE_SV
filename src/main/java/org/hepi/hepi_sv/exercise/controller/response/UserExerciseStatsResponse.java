package org.hepi.hepi_sv.exercise.controller.response;

import lombok.Builder;

@Builder
public record UserExerciseStatsResponse(
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
