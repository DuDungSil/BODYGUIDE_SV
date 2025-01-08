package org.hepi.hepi_sv.exercise.dto;

import java.util.List;

import org.hepi.hepi_sv.exercise.enums.MuscleGroupType;
import org.hepi.hepi_sv.exercise.enums.ThresholdType;

public record ExerciseAnalysisData(
    MuscleGroupType muscleGroupType,
    ThresholdType thresholdType,
    List<Double> thresholds
) {
    
}
