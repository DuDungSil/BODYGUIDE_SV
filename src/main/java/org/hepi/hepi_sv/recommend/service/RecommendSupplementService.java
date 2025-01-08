package org.hepi.hepi_sv.recommend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hepi.hepi_sv.nutrition.dto.NutrientProfile;
import org.hepi.hepi_sv.nutrition.service.NutrientRecommendService;
import org.hepi.hepi_sv.coupang.dto.CoupangProductDTO;
import org.hepi.hepi_sv.coupang.service.CoupangProductRecommendService;
import org.hepi.hepi_sv.recommend.dto.PurposeNutrientProfiles;
import org.hepi.hepi_sv.recommend.dto.RecommendSupplementRequest;
import org.hepi.hepi_sv.recommend.dto.RecommendSupplementResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendSupplementService {
    
    private final NutrientRecommendService nutrientRecommendService;
    private final CoupangProductRecommendService coupangProductRecommendService;
    
    public RecommendSupplementResponse getRecommendSupplementResponse(UUID userId, RecommendSupplementRequest request) {

        // 유저 db 조회 ? 운동 레포트를 추적할수있는가? ( 구현해야함 )
        int totalScore = 100; // 테스트용

        // 운동 수준에 따른 영양 성분 추천
        List<NutrientProfile> levelNutrientProfiles = nutrientRecommendService.getRecommendNutirientForLevel(totalScore);

        // 운동 목적에 따른 영양 성분 추천
        List<PurposeNutrientProfiles> purposeNutrientProfiles = getPurposeNutrientProfiles(request.exercisePurposeIds());

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
            List<NutrientProfile> profiles = nutrientRecommendService.getRecommendNutirientForPurpose(purposeId);

            PurposeNutrientProfiles purposeNutrientProfiles = new PurposeNutrientProfiles(purposeId, profiles);

            list.add(purposeNutrientProfiles);
        }

        return list;

    }

    // 운동 수준에 따른 상품 추천
    private List<CoupangProductDTO> getRecommendSupplementByNutrientProfiles(List<NutrientProfile> profiles) {

        List<CoupangProductDTO> list = new ArrayList<>();

        List<Integer> nutritientId_list = new ArrayList<>();
        for (NutrientProfile profile : profiles) {
            nutritientId_list.add(profile.getId());
        }

        nutritientId_list.stream().distinct().collect(Collectors.toList());

        for (int nutritionId : nutritientId_list) {

            List<CoupangProductDTO> shopProducts = coupangProductRecommendService.getRecommendSupplementByNutrition(nutritionId);

            for (CoupangProductDTO shopProduct : shopProducts) {
                list.add(shopProduct);
            }
        }

        Collections.shuffle(list);

        return list;
    }

    // 운동 목적에 따른 상품 추천
    private List<CoupangProductDTO> getRecommendSupplementByPurposeRecommends(List<PurposeNutrientProfiles> purposeProfiles) {

        List<CoupangProductDTO> list = new ArrayList<>();

        List<Integer> nutrient_list = new ArrayList<>();
        for (PurposeNutrientProfiles purposeProfile : purposeProfiles) {
            for (NutrientProfile profile : purposeProfile.profiles()) {
                nutrient_list.add(profile.getId());
            }
        }

        nutrient_list.stream().distinct().collect(Collectors.toList());

        for (int nutrient : nutrient_list) {
            List<CoupangProductDTO> shopProducts = coupangProductRecommendService.getRecommendSupplementByNutrition(nutrient);
            for (CoupangProductDTO shopProduct : shopProducts) {
                list.add(shopProduct);
            }
        }

        Collections.shuffle(list);

        return list;
    }
    
}
