package org.hepi.hepi_sv.product.service;

import java.util.List;

import org.hepi.hepi_sv.product.dto.ShopProductDTO;
import org.hepi.hepi_sv.product.repository.ProductQueryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductRecommendService {
    
    private final ProductQueryRepository productQueryRepository;

    // 3대영양소에 따른 음식 추천
    public List<ShopProductDTO> getRecommendFoodByMajorNutrient(int nutrientTypeId, int dietTypeId) {

        List<ShopProductDTO> shopProducts = productQueryRepository.selectFoodsByNutrientTypeAndDietType(nutrientTypeId, dietTypeId);

        return shopProducts;
    }

    // 영양성분에 따른 보충제 추천 ( 로직 고도화 필요 )
    public List<ShopProductDTO> getRecommendSupplementByNutrition(int nutrientId) {

        List<ShopProductDTO> shopProducts = productQueryRepository.selectSupplementsByNutrientName(nutrientId);

        return shopProducts;
    }

    
}
