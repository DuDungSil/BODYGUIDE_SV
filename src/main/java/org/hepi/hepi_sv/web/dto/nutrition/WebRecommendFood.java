package org.hepi.hepi_sv.web.dto.nutrition;

import java.util.List;

import org.hepi.hepi_sv.product.dto.ShopProductDTO;

import lombok.Data;

@Data
public class WebRecommendFood {
    List<ShopProductDTO> carbohydrate;
    List<ShopProductDTO> protein;
    List<ShopProductDTO> fat;
}
