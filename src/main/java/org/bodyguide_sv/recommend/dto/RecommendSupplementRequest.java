package org.bodyguide_sv.recommend.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "보충제 추천 Request")
public record RecommendSupplementRequest(
    @NotNull(message = "exercisePurposeIds는 필수 항목입니다.")
    @Size(min = 1, max = 5, message = "exercisePurposeIds는 최소 1개 이상, 최대 5개까지 입력 가능합니다.")
    List<Integer> exercisePurposeIds
) {
    
}
