package org.hepi.hepi_sv.recommend.dto;

import java.util.List;

public record RecommendSupplementRequest(
    List<Integer> exercisePurposeIds
) {
    
}
