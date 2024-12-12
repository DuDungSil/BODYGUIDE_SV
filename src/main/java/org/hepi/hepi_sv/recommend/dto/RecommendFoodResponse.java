package org.hepi.hepi_sv.recommend.dto;

import java.util.List;

import org.hepi.hepi_sv.product.dto.ShopProductDTO;

public record RecommendFoodResponse(
    List<ShopProductDTO> carbohydrate,
    List<ShopProductDTO> protein,
    List<ShopProductDTO> fat
) {
    
}
