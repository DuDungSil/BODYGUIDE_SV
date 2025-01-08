package org.hepi.hepi_sv.exercise.controller.response;

import java.util.List;

import org.hepi.hepi_sv.exercise.dto.ExerciseAbility;
import org.hepi.hepi_sv.exercise.dto.MuscleProfile;

public record ExerciseReportResponse(
    int totalScore,
    String totalLevel,
    Double topPercent,
    int bigThree,
    ExerciseAbility ability,
    List<MuscleProfile> weekMuscle
) {
    
}
