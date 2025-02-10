package org.bodyguide_sv.recommend.repository.custom;

import java.util.List;

import org.bodyguide_sv.nutrition.dto.NutrientProfile;

public interface RecommendNutrientCustomRepository {

    // 영양성분 추천 ( 운동 수준 )
    List<NutrientProfile> selectNutrientProfilesByLevel(int level);

    // 영양성분 추천 ( 운동 목적 )
    List<NutrientProfile> selectNutrientProfilesByPurposeName(String purpose);

    // 영양성분 추천 ( 운동 목적 )
    List<NutrientProfile> selectNutrientProfilesByPurposeId(int purposeId);

}
