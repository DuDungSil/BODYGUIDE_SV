package org.hepi.hepi_sv.web.dto.exercise;

import org.hepi.hepi_sv.exercise.dto.ExerciseProfile;

import lombok.Data;

@Data
public class ExerciseAbility {
    ExerciseProfile bench;
    ExerciseProfile squat;
    ExerciseProfile dead;
    ExerciseProfile overhead;
    ExerciseProfile pushup;
    ExerciseProfile pullup;

}
