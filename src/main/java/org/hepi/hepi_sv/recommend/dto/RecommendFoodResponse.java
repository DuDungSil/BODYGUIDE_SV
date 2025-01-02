package org.hepi.hepi_sv.recommend.dto;

import java.util.List;

import org.hepi.hepi_sv.product.dto.ShopProductDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "음식 추천 Response")
public record RecommendFoodResponse(
    @Schema(description = "탄수화물 추천 음식 상품")
    List<ShopProductDTO> carbohydrate,
    @Schema(description = "단백질 추천 음식 상품")
    List<ShopProductDTO> protein,
    @Schema(description = "지방 추천 음식 상품")
    List<ShopProductDTO> fat
) {
    
}
