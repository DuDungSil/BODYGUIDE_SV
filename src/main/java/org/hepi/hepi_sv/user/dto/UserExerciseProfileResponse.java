package org.hepi.hepi_sv.user.dto;

import lombok.Builder;

@Builder
public record UserExerciseProfileResponse(
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
