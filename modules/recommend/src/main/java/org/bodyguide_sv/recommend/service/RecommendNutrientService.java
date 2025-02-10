package org.bodyguide_sv.recommend.service;

import java.util.List;

import org.bodyguide_sv.nutrition.dto.NutrientProfile;
import org.bodyguide_sv.recommend.repository.custom.RecommendNutrientCustomRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendNutrientService {

    private final RecommendNutrientCustomRepository recommendNutrientCustomRepository;

    // 운동 수준에 따른 추천 
    public List<NutrientProfile> getRecommendNutirientForLevel(double totalScore) {

        int level = (int) ((totalScore / 20) + 1);
        if (level >= 7) {
            level = 6;
        }

        List<NutrientProfile> profiles = recommendNutrientCustomRepository.selectNutrientProfilesByLevel(level);

        return profiles;
    }

    // 운동 목적에 따른 추천 ( purpose name )
    public List<NutrientProfile> getRecommendNutirientForPurpose(String purpose) {

        List<NutrientProfile> profiles = recommendNutrientCustomRepository.selectNutrientProfilesByPurposeName(purpose);

        return profiles;
    }

    // 운동 목적에 따른 추천 ( purpose id )
    public List<NutrientProfile> getRecommendNutirientForPurpose(int purposeId) {

        List<NutrientProfile> profiles = recommendNutrientCustomRepository.selectNutrientProfilesByPurposeId(purposeId);

        return profiles;
    }

}
