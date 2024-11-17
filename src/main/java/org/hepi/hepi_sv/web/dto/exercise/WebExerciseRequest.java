package org.hepi.hepi_sv.web.dto.exercise;

import lombok.Data;

@Data
public class WebExerciseRequest {
    int id;
    String sex;
    int age;
    double height;
    double weight;
    ExerciseSet bench;
    ExerciseSet squat;
    ExerciseSet dead;
    ExerciseSet overhead;
    ExerciseSet pushup;
    ExerciseSet pullup;
    String[] supplePurpose;
}
