package org.bodyguide_sv.recommend.dto;

import java.util.List;

import org.bodyguide_sv.coupang.dto.CoupangProductDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "음식 추천 Response")
public record RecommendFoodResponse(
    @Schema(description = "탄수화물 추천 음식 상품")
    List<CoupangProductDTO> carbohydrate,
    @Schema(description = "단백질 추천 음식 상품")
    List<CoupangProductDTO> protein,
    @Schema(description = "지방 추천 음식 상품")
    List<CoupangProductDTO> fat
) {
    
}
