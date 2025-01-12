package org.bodyguide_sv.exercise.controller.response;

import java.time.LocalDateTime;

public record ExerciseTotalPerformanceResponse(
    double totalScore,
    LocalDateTime scoreUpdatedAt,
    String exerciseLevel,
    LocalDateTime levelUpdatedAt,
    Double bigThree,
    LocalDateTime bigThreeUpdatedAt
) {
    
}
