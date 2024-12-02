package org.hepi.hepi_sv.recommend.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.hepi.hepi_sv.product.dto.ShopProductDTO;
import org.hepi.hepi_sv.product.service.ProductRecommendService;
import org.hepi.hepi_sv.recommend.dto.RecommendFoodResponse;
import org.hepi.hepi_sv.user.dto.UserNutritionProfileDTO;
import org.hepi.hepi_sv.user.service.UserNutritionProfileService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendFoodService {
    
    private final UserNutritionProfileService userNutritionProfileService;
    private final ProductRecommendService productRecommendService;
    
    public RecommendFoodResponse getRecommendFoodResponse(UUID userId) {

        // 3대영양소에 따른 음식 추천

        // 1. db 프로필 가져오기
        UserNutritionProfileDTO userNutritionProfile = userNutritionProfileService.getUserNutritionProfileDTO(userId);

        // 2. 추천

        int dietTypeId = userNutritionProfile.dietId() == 5 ? 5 : 1;

        List<ShopProductDTO> carbohydrate = productRecommendService.getRecommendFoodByMajorNutrient(1, dietTypeId);
        List<ShopProductDTO> protein = productRecommendService.getRecommendFoodByMajorNutrient(2, dietTypeId);
        List<ShopProductDTO> fat = productRecommendService.getRecommendFoodByMajorNutrient(3, dietTypeId);

        Collections.shuffle(carbohydrate);
        Collections.shuffle(protein);
        Collections.shuffle(fat);

        // 상품 4개만 자르기 ( 수정 필요 )


        return new RecommendFoodResponse(carbohydrate, protein, fat);
    }

}
