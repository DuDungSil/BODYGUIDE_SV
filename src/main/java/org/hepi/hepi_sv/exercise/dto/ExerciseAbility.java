package org.hepi.hepi_sv.exercise.dto;

import lombok.Data;

@Data
public class ExerciseAbility {
    ExerciseAnalysisProfile bench;
    ExerciseAnalysisProfile squat;
    ExerciseAnalysisProfile dead;
    ExerciseAnalysisProfile overhead;
    ExerciseAnalysisProfile pushup;
    ExerciseAnalysisProfile pullup;
}
