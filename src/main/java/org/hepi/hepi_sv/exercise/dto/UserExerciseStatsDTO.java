package org.hepi.hepi_sv.exercise.dto;

import lombok.Builder;

@Builder
public record UserExerciseStatsDTO(
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
