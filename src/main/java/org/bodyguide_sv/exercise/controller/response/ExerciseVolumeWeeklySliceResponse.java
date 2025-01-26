package org.bodyguide_sv.exercise.controller.response;

import java.util.List;

public record ExerciseVolumeWeeklySliceResponse(
        int currentPage,       // 현재 페이지 번호
        int pageSize,          // 페이지당 항목 수
        boolean hasNext, // 다음 페이지 여부
        List<ExerciseVolumeWeeklyResponse> volumes
) {
    
    public static record ExerciseVolumeWeeklyResponse(
        Long id,
        Integer year,
        Integer week,
        double volume
    ) {
        
    }

}
