package org.bodyguide_sv.exercise.dto;

import java.util.List;

import org.bodyguide_sv.exercise.enums.MuscleGroupType;
import org.bodyguide_sv.exercise.enums.ThresholdType;

public record ExerciseAnalysisData(
    MuscleGroupType muscleGroupType,
    ThresholdType thresholdType,
    List<Double> thresholds
) {
    
}
