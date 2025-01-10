package org.bodyguide_sv.exercise.controller.response;

import java.time.LocalDateTime;
import java.util.List;

public record ExerciseRecordGroupListResponse(
    List<ExerciseRecordGroupResponse> recordGroupList
) {

    public static record ExerciseRecordGroupResponse(
        int groupId,
        LocalDateTime exerciseDate,
        List<ExerciseRecordResponse> exercises
    ) {
    }

    public static record ExerciseRecordResponse(
        int exerciseId,
        List<ExerciseSetResponse> sets,
        double prevBestWeight,
        int prevBestReps
    ) {
    }

    public static record ExerciseSetResponse(
        int set,
        double weight,
        int reps,
        double score,
        double strength
    ) {
    }
}

