package org.bodyguide_sv.exercise.dto;

import org.bodyguide_sv.exercise.enums.MuscleGroupType;

import lombok.Data;

@Data
public class ExerciseAnalysisProfile {
    int exerId;
    MuscleGroupType muscleGroupType;      // 관련 근육
    Double score;
    String level;
    Double strength;  // 1RM or Max Reps
    int average;
}
