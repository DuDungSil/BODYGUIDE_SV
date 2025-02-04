package org.bodyguide_sv.exercise.controller.response;

import java.util.List;

import org.bodyguide_sv.exercise.dto.ExerciseMuscleAbility;
import org.bodyguide_sv.exercise.dto.MuscleProfile;

public record ExerciseReportResponse(
    int totalScore,
    String totalLevel,
    Double topPercent,
    ExerciseMuscleAbility ability,
    List<MuscleProfile> weekMuscle
) {
    
}
