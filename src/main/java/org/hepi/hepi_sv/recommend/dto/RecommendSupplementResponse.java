package org.hepi.hepi_sv.recommend.dto;

import java.util.List;

import org.hepi.hepi_sv.nutrition.dto.NutrientProfile;
import org.hepi.hepi_sv.coupang.dto.CoupangProductDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "보충제 추천 Response")
public record RecommendSupplementResponse(
    @Schema(description = "운동 수준에 따른 영양 성분 프로필")
    List<NutrientProfile> levelNutrientProfiles,
    @Schema(description = "운동 목적에 따른 영양 성분 프로필")
    List<PurposeNutrientProfiles> purposeNutrientProfiles,
    @Schema(description = "운동 수준에 따른 추천 보충제 상품")
    List<CoupangProductDTO> levelProducts,
    @Schema(description = "운동 목적에 따른 추천 보충제 상품")
    List<CoupangProductDTO> purporseProducts
) {
    
}
