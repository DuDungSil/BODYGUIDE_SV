package org.bodyguide_sv.coupang.service;

import java.util.List;

import org.bodyguide_sv.coupang.dto.CoupangProductDTO;
import org.bodyguide_sv.coupang.repository.CoupangProductQueryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CoupangProductRecommendService {
    
    private final CoupangProductQueryRepository coupangProductQueryRepository;

    // 3대영양소에 따른 음식 추천
    public List<CoupangProductDTO> getRecommendFoodByMajorNutrient(int nutrientTypeId, int dietTypeId) {

        List<CoupangProductDTO> coupangProducts = coupangProductQueryRepository.selectFoodsByNutrientTypeAndDietType(nutrientTypeId, dietTypeId);

        return coupangProducts;
    }

    // 영양성분에 따른 보충제 추천 ( 로직 고도화 필요 )
    public List<CoupangProductDTO> getRecommendSupplementByNutrition(int nutrientId) {

        List<CoupangProductDTO> coupangProducts = coupangProductQueryRepository.selectSupplementsByNutrientName(nutrientId);

        return coupangProducts;
    }

    
}
