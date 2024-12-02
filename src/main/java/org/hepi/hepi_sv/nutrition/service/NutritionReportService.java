package org.hepi.hepi_sv.nutrition.service;

import java.util.UUID;

import org.hepi.hepi_sv.nutrition.dto.NutritionReportResponse;
import org.hepi.hepi_sv.user.dto.UserNutritionProfileDTO;
import org.hepi.hepi_sv.user.dto.UserProfileDTO;
import org.hepi.hepi_sv.user.service.UserNutritionProfileService;
import org.hepi.hepi_sv.user.service.UserProfileService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NutritionReportService {
    
    private final UserProfileService userProfileService;
    private final UserNutritionProfileService userNutritionProfileService;
    private final NutritionAnalysisService nutritionAnalysisService;

    public NutritionReportResponse getReportResponse(UUID userId) {
        
        // 1. db 에서 프로필 가져오기

        // db 에서 유저 프로필 가져오기
        UserProfileDTO userProfile = userProfileService.getUserProfileDTO(userId);
        // db 에서 영양 프로필 가져오기
        UserNutritionProfileDTO userNutritionProfile = userNutritionProfileService.getUserNutritionProfileDTO(userId);

        // 2. 분석

        // BMI
        Double bmi = nutritionAnalysisService.calculateBMI(userProfile.height(), userProfile.weight());

        // 섭취 목적 ( 추론해야함 )
        //String intakeGoal = userNutritionProfile.

        // 추천 대사량
        

        // 식단 영양소 비율

        // 끼니당 영양소 비율

        // 식사 간격

        // 추천 급원

        return new NutritionReportResponse(bmi, null, 0.0, null, null, null);
    }

}
