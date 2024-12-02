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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/nutri")
public class NutritionController {

    private final NutritionReportService nutritionReportService; 

    // 섭취 분석 결과 가져오기
    @GetMapping("/analysis/report")
    public ResponseEntity<NutritionReportResponse> getNutritionReport(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        NutritionReportResponse nutritionReportResponse = nutritionReportService
                .getReportResponse(UUID.fromString(userDetails.getUsername()));

        return ResponseEntity.ok(nutritionReportResponse);
    }

}
