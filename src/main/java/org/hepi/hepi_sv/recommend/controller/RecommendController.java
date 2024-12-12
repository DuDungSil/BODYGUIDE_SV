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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recommend")
@Tag(name = "Recommend", description = "추천")
public class RecommendController {
    
    private final RecommendFoodService recommendFoodService;
    private final RecommendSupplementService recommendSupplementService;

    @GetMapping("/food")
    @Operation(summary = "급원 추천", description = "급원 추천")
    public ResponseEntity<RecommendFoodResponse> getRecommendFood(@AuthenticationPrincipal UserDetails userDetails) {
        RecommendFoodResponse response = recommendFoodService.getRecommendFoodResponse(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/supplement")
    @Operation(summary = "영양성분 & 보충제 추천", description = "운동 목적에 따른 영양성분 & 보충제 추천")
    public ResponseEntity<RecommendSupplementResponse> getRecommendSupplement(@AuthenticationPrincipal UserDetails userDetails, @RequestBody RecommendSupplementRequest request) {
        RecommendSupplementResponse response = recommendSupplementService.getRecommendSupplementResponse(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok(response);
    }

}
