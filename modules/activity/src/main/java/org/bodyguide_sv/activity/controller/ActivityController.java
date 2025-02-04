package org.bodyguide_sv.activity.controller;

import java.util.UUID;

import org.bodyguide_sv.activity.controller.response.ActivityProfileResponse;
import org.bodyguide_sv.activity.service.ActivityService;
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
@RequestMapping("/activity")
@Tag(name = "Activity", description = "유저 활동 관련")
public class ActivityController {
    
    private final ActivityService activityService;

    // 유저 활동 기록 가져오기
    @GetMapping("/profile")
    @Operation(summary = "유저 활동 기록 프로필", description = "유저의 활동 기록 관련")
    public ResponseEntity<ActivityProfileResponse> getActivityProfile(@AuthenticationPrincipal UserDetails userDetails) {

        ActivityProfileResponse response = activityService.getActivityProfileResponse(UUID.fromString(userDetails.getUsername()));

        return ResponseEntity.ok(response);
    }

}
