package org.hepi.hepi_sv.exercise.dto;

import java.util.List;

public record ExerciseReportResponse(
    int totalScore,
    String totalLevel,
    Double topPercent,
    int bigThree,
    ExerciseAbility ability,
    List<MuscleProfile> weekMuscle
) {
    
}
