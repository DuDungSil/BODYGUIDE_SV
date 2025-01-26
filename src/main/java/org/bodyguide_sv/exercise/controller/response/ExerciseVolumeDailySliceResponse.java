package org.bodyguide_sv.exercise.controller.response;

import java.time.LocalDate;
import java.util.List;

public record ExerciseVolumeDailySliceResponse(
        int currentPage,       // 현재 페이지 번호
        int pageSize,          // 페이지당 항목 수
        boolean hasNext, // 다음 페이지 여부
        List<ExerciseVolumeDailyResponse> volumes
    )
    {
    
        public static record ExerciseVolumeDailyResponse(
            Long id,
            LocalDate date,
            double volume
        ) {
            
        }

}
