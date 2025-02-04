package org.bodyguide_sv.nutrition.controller;

import java.util.UUID;

import org.bodyguide_sv.nutrition.controller.request.UserNutritionProfileRequest;
import org.bodyguide_sv.nutrition.controller.response.NutritionReportResponse;
import org.bodyguide_sv.nutrition.controller.response.UserNutritionProfileResponse;
import org.bodyguide_sv.nutrition.service.NutritionReportService;
import org.bodyguide_sv.nutrition.service.UserNutritionProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/nutri")
@Tag(name = "Nutrition", description = "영양 관련")
public class NutritionController {

    private final UserNutritionProfileService userNutritionProfileService;
    private final NutritionReportService nutritionReportService;

        @GetMapping("/profile/nutrition")
    @Operation(summary = "유저 영양 프로필 조회", description = "유저 영양 프로필 정보를 조회")
    public ResponseEntity<UserNutritionProfileResponse> getNutritionProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserNutritionProfileResponse userNutritionProfileResponse = userNutritionProfileService
                .getUserNutritionProfileResponse(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(userNutritionProfileResponse);
    }

    @PatchMapping("/profile/nutrition")
    @Operation(summary = "유저 영양 프로필 부분 업데이트", description = "유저 영양 프로필 정보를 부분 업데이트")
    public ResponseEntity<String> updateNutritionProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserNutritionProfileRequest request) {
        userNutritionProfileService.updateUserNutritionProfile(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok("유저 영양 프로필 업데이트 완료");
    }

    @GetMapping("/analysis/report")
    @Operation(summary = "영양 분석 리포트 결과", description = "DB의 유저 프로필 정보들을 통해 영양 분석 결과를 조회")
    public ResponseEntity<NutritionReportResponse> getNutritionReport(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        NutritionReportResponse nutritionReportResponse = nutritionReportService
                .getReportResponse(UUID.fromString(userDetails.getUsername()));

        return ResponseEntity.ok(nutritionReportResponse);
    }

}
