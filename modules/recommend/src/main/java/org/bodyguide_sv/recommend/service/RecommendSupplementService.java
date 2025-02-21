package org.bodyguide_sv.recommend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bodyguide_sv.coupang.dto.CoupangProductDTO;
import org.bodyguide_sv.coupang.service.CoupangProductRecommendService;
import org.bodyguide_sv.nutrition.dto.NutrientProfile;
import org.bodyguide_sv.recommend.controller.request.RecommendSupplementRequest;
import org.bodyguide_sv.recommend.controller.response.RecommendSupplementResponse;
import org.bodyguide_sv.recommend.dto.PurposeNutrientProfiles;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendSupplementService {

    private final RecommendNutrientService recommendNutrientService;
    private final CoupangProductRecommendService coupangProductRecommendService;

    public RecommendSupplementResponse getRecommendSupplementResponse(UUID userId, RecommendSupplementRequest request) {

        List<Integer> exercisePurposeIds = request.exercisePurposeIds();
        double totalScore = request.totalScore();

        // 운동 수준에 따른 영양 성분 추천
        List<NutrientProfile> levelNutrientProfiles = recommendNutrientService.getRecommendNutirientForLevel(totalScore);

        // 운동 목적에 따른 영양 성분 추천
        List<PurposeNutrientProfiles> purposeNutrientProfiles = getPurposeNutrientProfiles(exercisePurposeIds);

        // 운동 수준에 따른 상품 추천
        List<CoupangProductDTO> levelProducts = getRecommendSupplementByNutrientProfiles(levelNutrientProfiles);

        // 운동 목적에 따른 상품 추천
        List<CoupangProductDTO> purposeProducts = getRecommendSupplementByPurposeRecommends(purposeNutrientProfiles);

        // 상품 4개만 자르기 ( 수정 필요 )
        return new RecommendSupplementResponse(levelNutrientProfiles, purposeNutrientProfiles, levelProducts, purposeProducts);

    }

    // 운동 목적에 따른 영양 성분 추천
    private List<PurposeNutrientProfiles> getPurposeNutrientProfiles(List<Integer> purposeIds) {

        List<PurposeNutrientProfiles> list = new ArrayList<>();

        for (int purposeId : purposeIds) {

            // db 쿼리 키 : purpose
            List<NutrientProfile> profiles = recommendNutrientService.getRecommendNutirientForPurpose(purposeId);

            PurposeNutrientProfiles purposeNutrientProfiles = new PurposeNutrientProfiles(purposeId, profiles);

            list.add(purposeNutrientProfiles);
        }

        return list;

    }

    // 운동 수준에 따른 상품 추천
    private List<CoupangProductDTO> getRecommendSupplementByNutrientProfiles(List<NutrientProfile> profiles) {
        List<Integer> nutrientIds = profiles.stream()
                .map(NutrientProfile::getId)
                .distinct()
                .collect(Collectors.toList());

        return coupangProductRecommendService.getRecommendSupplementsByNutritionIds(nutrientIds);
    }

    // 운동 목적에 따른 상품 추천
    private List<CoupangProductDTO> getRecommendSupplementByPurposeRecommends(List<PurposeNutrientProfiles> purposeProfiles) {
        List<Integer> nutrientIds = purposeProfiles.stream()
                .flatMap(purposeProfile -> purposeProfile.profiles().stream())
                .map(NutrientProfile::getId)
                .distinct()
                .collect(Collectors.toList());

        return coupangProductRecommendService.getRecommendSupplementsByNutritionIds(nutrientIds);
    }

}
