package org.hepi.hepi_sv.recommend.service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.hepi.hepi_sv.nutrition.dto.UserNutritionProfileDTO;
import org.hepi.hepi_sv.nutrition.service.UserNutritionProfileService;
import org.hepi.hepi_sv.coupang.dto.CoupangProductDTO;
import org.hepi.hepi_sv.coupang.service.CoupangProductRecommendService;
import org.hepi.hepi_sv.recommend.dto.RecommendFoodResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendFoodService {
    
    private final UserNutritionProfileService userNutritionProfileService;
    private final CoupangProductRecommendService coupangProductRecommendService;
    
    public RecommendFoodResponse getRecommendFoodResponse(UUID userId) {

        // 3대영양소에 따른 음식 추천

        // 1. db 프로필 가져오기
        UserNutritionProfileDTO userNutritionProfile = userNutritionProfileService.getUserNutritionProfileDTO(userId);

        // 2. 추천

        int dietTypeId = userNutritionProfile.dietId() == 5 ? 5 : 1;

        List<CoupangProductDTO> carbohydrate = coupangProductRecommendService.getRecommendFoodByMajorNutrient(1, dietTypeId);
        List<CoupangProductDTO> protein = coupangProductRecommendService.getRecommendFoodByMajorNutrient(2, dietTypeId);
        List<CoupangProductDTO> fat = coupangProductRecommendService.getRecommendFoodByMajorNutrient(3, dietTypeId);

        Collections.shuffle(carbohydrate);
        Collections.shuffle(protein);
        Collections.shuffle(fat);

        // 상품 4개만 자르기 ( 수정 필요 )


        return new RecommendFoodResponse(carbohydrate, protein, fat);
    }

}
