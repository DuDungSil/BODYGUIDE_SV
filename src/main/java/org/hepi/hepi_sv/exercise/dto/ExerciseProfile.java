package org.hepi.hepi_sv.exercise.dto;

import lombok.Data;

@Data
public class ExerciseProfile {
    String part;      // 운동 부위
    Double score;
    String level;
    Double strength;  // 1RM or Max Reps
    int average;
}
