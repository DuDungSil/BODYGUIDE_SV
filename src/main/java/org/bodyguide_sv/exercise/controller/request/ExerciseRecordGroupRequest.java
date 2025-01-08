package org.bodyguide_sv.exercise.controller.request;

import java.time.LocalDateTime;
import java.util.List;

public record ExerciseRecordGroupRequest(
    int groupId,
    LocalDateTime exerciseDate,
    List<ExerciseRecordRequest> exercises
) {
    public static record ExerciseRecordRequest(
        int exerciseId,
        List<ExerciseSetRequest> sets
    ) {
    }

    public static record ExerciseSetRequest(
        int set,
        double weight,
        int reps
    ) {
    }
}
