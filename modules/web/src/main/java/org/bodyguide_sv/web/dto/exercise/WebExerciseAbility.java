package org.bodyguide_sv.web.dto.exercise;

import org.bodyguide_sv.exercise.dto.ExerciseAnalysisProfile;

import lombok.Data;

@Data
public class WebExerciseAbility {

    ExerciseAnalysisProfile bench;
    ExerciseAnalysisProfile squat;
    ExerciseAnalysisProfile dead;
    ExerciseAnalysisProfile overhead;
    ExerciseAnalysisProfile pushup;
    ExerciseAnalysisProfile pullup;
}
