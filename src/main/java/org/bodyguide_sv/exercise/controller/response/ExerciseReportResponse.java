package org.bodyguide_sv.exercise.controller.response;

import java.util.List;

import org.bodyguide_sv.exercise.dto.ExerciseAbility;
import org.bodyguide_sv.exercise.dto.MuscleProfile;

public record ExerciseReportResponse(
    int totalScore,
    String totalLevel,
    Double topPercent,
    int bigThree,
    ExerciseAbility ability,
    List<MuscleProfile> weekMuscle
) {
    
}
