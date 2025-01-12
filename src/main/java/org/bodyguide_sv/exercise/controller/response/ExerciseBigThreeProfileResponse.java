package org.bodyguide_sv.exercise.controller.response;

import java.time.LocalDateTime;

public record ExerciseBigThreeProfileResponse(
    double squatScore,
    double squatWeight,
    double squatReps,
    LocalDateTime squatUpdatedAt,
    double deadLiftScore,
    double deadLiftWeight,
    double deadLiftReps,
    LocalDateTime deadLiftUpdatedAt,
    double benchPressScore,
    double benchPressWeight,
    double benchPressReps,
    LocalDateTime benchPressUpdatedAt
) {
    
}
