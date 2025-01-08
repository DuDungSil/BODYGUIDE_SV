package org.hepi.hepi_sv.activity.controller;

import java.util.UUID;

import org.hepi.hepi_sv.activity.dto.ActivityProfileResponse;
import static org.hepi.hepi_sv.activity.enums.ActivityType.EXERCISE;
import org.hepi.hepi_sv.activity.event.ActivityCompletedEvent;
import org.hepi.hepi_sv.activity.service.ActivityService;
import org.springframework.context.ApplicationEventPublisher;
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
    
    private final ApplicationEventPublisher eventPublisher;
    private final ActivityService activityService;

    @GetMapping("/test")
    public ResponseEntity<String> test(@AuthenticationPrincipal UserDetails userDetails) {

        ActivityCompletedEvent event = new ActivityCompletedEvent(UUID.fromString(userDetails.getUsername()), EXERCISE);
        eventPublisher.publishEvent(event);
    
        return ResponseEntity.ok("성공");
    }

    // 유저 활동 기록 가져오기
    @GetMapping("/profile")
    @Operation(summary = "유저 활동 기록 프로필", description = "유저의 활동 기록 관련")
    public ResponseEntity<ActivityProfileResponse> getActivityProfile(@AuthenticationPrincipal UserDetails userDetails) {

        ActivityProfileResponse response = activityService.getActivityProfileResponse(UUID.fromString(userDetails.getUsername()));

        return ResponseEntity.ok(response);
    }

}