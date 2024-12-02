package org.hepi.hepi_sv.user.controller;

import java.util.UUID;

import org.hepi.hepi_sv.user.dto.UserExerciseProfileRequest;
import org.hepi.hepi_sv.user.dto.UserExerciseProfileResponse;
import org.hepi.hepi_sv.user.dto.UserNutritionProfileRequest;
import org.hepi.hepi_sv.user.dto.UserNutritionProfileResponse;
import org.hepi.hepi_sv.user.dto.UserProfileRequest;
import org.hepi.hepi_sv.user.dto.UserProfileResponse;
import org.hepi.hepi_sv.user.service.UserExerciseProfileService;
import org.hepi.hepi_sv.user.service.UserNutritionProfileService;
import org.hepi.hepi_sv.user.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserProfileService userProfileService;
    private final UserExerciseProfileService userExerciseProfileService;
    private final UserNutritionProfileService userNutritionProfileService;

    // 유저 프로필 가져오기
    @GetMapping("/profile/basic")
    public ResponseEntity<UserProfileResponse> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserProfileResponse userProfileResponse = userProfileService.getUserProfileResponse(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(userProfileResponse);
    }

    // 유저 프로필 업데이트 ( 일부 필드만 업데이트 가능 )
    @PatchMapping("/profile/basic")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserProfileRequest request) {
        userProfileService.updateUserProfile(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok("유저 프로필 업데이트 완료");
    }

    // 유저 운동 프로필 가져오기
    @GetMapping("/profile/exercise")
    public ResponseEntity<UserExerciseProfileResponse> getExerciseProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserExerciseProfileResponse userExerciseProfileResponse = userExerciseProfileService
                .getUserExerciseProfileResponse(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(userExerciseProfileResponse);
    }

    // 유저 운동 프로필 업데이트
    @PostMapping("/profile/exercise")
    public ResponseEntity<String> updateExerciseProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserExerciseProfileRequest request) {
        userExerciseProfileService.updateUserExerciseProfile(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok("유저 운동 프로필 업데이트 완료");
    }

    // 유저 영양 프로필 가져오기
    @GetMapping("/profile/nutrition")
    public ResponseEntity<UserNutritionProfileResponse> getNutritionProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserNutritionProfileResponse userNutritionProfileResponse = userNutritionProfileService
                .getUserNutritionProfileResponse(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(userNutritionProfileResponse);
    }

    // 유저 영양 프로필 업데이트
    @PostMapping("/profile/nutrition")
    public ResponseEntity<String> updateNutritionProfile(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserNutritionProfileRequest request) {
        userNutritionProfileService.updateUserNutritionProfile(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok("유저 영양 프로필 업데이트 완료");
    }

}
