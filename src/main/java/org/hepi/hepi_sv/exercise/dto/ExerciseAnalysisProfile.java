package org.hepi.hepi_sv.exercise.dto;

import lombok.Data;

@Data
public class ExerciseAnalysisProfile {
    int exerId;
    String muscle;      // 관련 근육
    Double score;
    String level;
    Double strength;  // 1RM or Max Reps
    int average;
}
