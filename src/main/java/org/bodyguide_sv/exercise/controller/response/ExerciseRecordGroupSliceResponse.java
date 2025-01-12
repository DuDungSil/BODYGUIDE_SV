package org.bodyguide_sv.exercise.controller.response;

import java.time.LocalDateTime;
import java.util.List;

public record ExerciseRecordGroupSliceResponse(
    int currentPage,       // 현재 페이지 번호
    int pageSize,          // 페이지당 항목 수
    boolean hasNext,       // 다음 페이지 여부
    // int totalPages,        // 총 페이지 수
    // long totalItems,        // 총 항목 수
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

