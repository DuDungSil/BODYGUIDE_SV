package org.hepi.hepi_sv.user.controller;

import java.util.UUID;

import org.hepi.hepi_sv.exercise.dto.UserExerciseStatsRequest;
import org.hepi.hepi_sv.exercise.dto.UserExerciseStatsResponse;
import org.hepi.hepi_sv.exercise.service.UserExerciseStatsService;
import org.hepi.hepi_sv.nutrition.dto.UserNutritionProfileRequest;
import org.hepi.hepi_sv.nutrition.dto.UserNutritionProfileResponse;
import org.hepi.hepi_sv.nutrition.service.UserNutritionProfileService;
import org.hepi.hepi_sv.user.dto.UserProfileRequest;
import org.hepi.hepi_sv.user.dto.UserProfileResponse;
import org.hepi.hepi_sv.user.service.UserProfileService;
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
@RequestMapping("/user")
@Tag(name = "User", description = "유저 정보")
public class UserController {

    private final UserProfileService userProfileService;

    @GetMapping("/profile")
    @Operation(summary = "유저 기본 프로필 조회", description = "유저 기본 프로필 정보를 조회")
    public ResponseEntity<UserProfileResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserProfileResponse userProfileResponse = userProfileService.getUserProfileResponse(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(userProfileResponse);
    }

    @PatchMapping("/profile")
    @Operation(summary = "유저 기본 프로필 부분 업데이트", description = "유저 기본 프로필 정보를 부분 업데이트")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserProfileRequest request) {
        userProfileService.updateUserProfile(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok("유저 프로필 업데이트 완료");
    }
    
}
