package org.hepi.hepi_sv.product.service;

import java.util.List;

import org.hepi.hepi_sv.product.entity.ShopProduct;
import org.hepi.hepi_sv.product.repository.ProductQueryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductRecommendService {
    
    private final ProductQueryRepository productQueryRepository;

    // 3대영양소에 따른 음식 추천
    public List<ShopProduct> getRecommendFoodByMajorNutrient(String nutrientType, String dietType) {

        List<ShopProduct> shopProducts = productQueryRepository.selectFoodsByNutrientTypeAndDietType(nutrientType, dietType);

        return shopProducts;
    }

    // 영양성분에 따른 보충제 추천 ( 로직 고도화 필요 )
    public List<ShopProduct> getRecommendSupplementByNutrition(String nutrition) {

        List<ShopProduct> shopProducts = productQueryRepository.selectSupplementsByNutrientName(nutrition);

        return shopProducts;
    }

    
}
