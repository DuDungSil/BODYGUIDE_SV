package org.hepi.hepi_sv.exercise.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ExerciseRecordGroupDTO(
    int groupId,
    LocalDateTime exerciseDate,
    List<ExerciseRecrod> recordGroup
) {
    public static record ExerciseRecrod(
        int exerciseId,
        List<ExerciseSet> set
    ) {
    }
    
    public static record ExerciseSet(
        int set,
        double weight,
        int reps,
        double score,
        double strength
    ) {
    }
}
