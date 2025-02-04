package org.bodyguide_sv.weight.controller.response;

import java.time.LocalDateTime;
import java.util.List;

public record WeightRecordSliceResponse(
    int currentPage,       // 현재 페이지 번호
    int pageSize,          // 페이지당 항목 수
    boolean hasNext,       // 다음 페이지 여부
    List<WeightRecordResponse> weightRecordList
) {
    
    public static record WeightRecordResponse(
        Long historyId,
        Double weight,
        LocalDateTime recordDate
    ) {
        
    }

}
