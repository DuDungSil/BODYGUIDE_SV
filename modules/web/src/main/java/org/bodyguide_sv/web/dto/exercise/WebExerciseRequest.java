package org.bodyguide_sv.web.dto.exercise;

import lombok.Data;

@Data
public class WebExerciseRequest {
    int id;
    String sex;
    int age;
    double height;
    double weight;
    WebExerciseSet bench;
    WebExerciseSet squat;
    WebExerciseSet dead;
    WebExerciseSet overhead;
    WebExerciseSet pushup;
    WebExerciseSet pullup;
    String[] supplePurpose;
}
