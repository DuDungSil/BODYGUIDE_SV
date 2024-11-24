package org.hepi.hepi_sv.nutrition.service;

import java.util.List;

import org.hepi.hepi_sv.nutrition.dto.NutrientProfile;
import org.hepi.hepi_sv.nutrition.repository.NutritionQueryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NutrientRecommendService {
    
    private final NutritionQueryRepository nutritionQueryRepository;

    // 운동 수준에 따른 추천 ( 0 <= totalScore <= 120)
    public List<NutrientProfile> getRecommendNutirientForLevel(int totalScore) {

        int level = (totalScore / 20) + 1;
        if (level >= 7) {
            level = 6;
        }

        List<NutrientProfile> profiles = nutritionQueryRepository.selectNutrientProfilesByLevel(level);

        return profiles;
    }

    // 운동 목적에 따른 추천
    public List<NutrientProfile> getRecommendNutirientForPurpose(String purpose) {

        List<NutrientProfile> profiles = nutritionQueryRepository.selectNutrientProfilesByPurpose(purpose);

        return profiles;
    }

}
