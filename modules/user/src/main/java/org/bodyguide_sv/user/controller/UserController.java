package org.bodyguide_sv.user.controller;

import java.util.UUID;

import org.bodyguide_sv.user.controller.request.UserProfileRequest;
import org.bodyguide_sv.user.controller.response.UserProfileResponse;
import org.bodyguide_sv.user.service.UserProfileService;
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
        UserProfileResponse response = userProfileService.getUserProfileResponse(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/profile")
    @Operation(summary = "유저 기본 프로필 부분 업데이트", description = "유저 기본 프로필 정보를 부분 업데이트")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserProfileRequest request) {
        userProfileService.updateUserProfile(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok("유저 프로필 업데이트 완료");
    }
    
}
