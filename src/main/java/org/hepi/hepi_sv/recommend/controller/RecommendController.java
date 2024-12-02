package org.hepi.hepi_sv.recommend.controller;

import java.util.UUID;

import org.hepi.hepi_sv.recommend.dto.RecommendFoodResponse;
import org.hepi.hepi_sv.recommend.dto.RecommendSupplementRequest;
import org.hepi.hepi_sv.recommend.dto.RecommendSupplementResponse;
import org.hepi.hepi_sv.recommend.service.RecommendFoodService;
import org.hepi.hepi_sv.recommend.service.RecommendSupplementService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recommend")
public class RecommendController {
    
    private final RecommendFoodService recommendFoodService;
    private final RecommendSupplementService recommendSupplementService;

    // 음식 추천
    @GetMapping("/food")
    public ResponseEntity<RecommendFoodResponse> getRecommendFood(@AuthenticationPrincipal UserDetails userDetails) {
        RecommendFoodResponse response = recommendFoodService.getRecommendFoodResponse(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(response);
    }

    // 영양성분, 보충제 추천
    @GetMapping("/supplement")
    public ResponseEntity<RecommendSupplementResponse> getRecommendSupplement(@AuthenticationPrincipal UserDetails userDetails, @RequestBody RecommendSupplementRequest request) {
        RecommendSupplementResponse response = recommendSupplementService.getRecommendSupplementResponse(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok(response);
    }

}
