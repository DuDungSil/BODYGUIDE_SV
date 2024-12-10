package org.hepi.hepi_sv.nutrition.controller;

import java.util.UUID;

import org.hepi.hepi_sv.nutrition.dto.NutritionReportResponse;
import org.hepi.hepi_sv.nutrition.service.NutritionReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
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

    private final NutritionReportService nutritionReportService; 

    // 섭취 분석 결과 가져오기
    @GetMapping("/analysis/report")
    @Operation(summary = "영양 분석 리포트 결과", description = "DB의 유저 프로필 정보들을 통해 영양 분석 결과를 조회")
    public ResponseEntity<NutritionReportResponse> getNutritionReport(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        NutritionReportResponse nutritionReportResponse = nutritionReportService
                .getReportResponse(UUID.fromString(userDetails.getUsername()));

        return ResponseEntity.ok(nutritionReportResponse);
    }

}
