package org.hepi.hepi_sv.nutrition.service;

import java.util.List;

import org.hepi.hepi_sv.nutrition.repository.NutritionQueryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SourceRecommendService {
    
    private final NutritionQueryRepository nutritionQueryRepository;

    // 추천 급원 DB에서 가져오기
    public List<String> getRecommendSource(String nutrientType, String dietType) {
        
        List<String> sources = nutritionQueryRepository.selectSourceByNutrientAndDiet(nutrientType, dietType);

        return sources;
    }

}
