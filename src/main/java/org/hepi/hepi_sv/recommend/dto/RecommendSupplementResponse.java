package org.hepi.hepi_sv.recommend.dto;

import java.util.List;

import org.hepi.hepi_sv.nutrition.dto.NutrientProfile;
import org.hepi.hepi_sv.product.dto.ShopProductDTO;

public record RecommendSupplementResponse(
    List<NutrientProfile> levelNutrientProfiles,
    List<PurposeNutrientProfiles> purposeNutrientProfiles,
    List<ShopProductDTO> levelProducts,
    List<ShopProductDTO> purporseProducts
) {
    
}
