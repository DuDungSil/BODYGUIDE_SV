package org.bodyguide_sv.activity.controller;

import java.util.UUID;

import org.bodyguide_sv.activity.controller.response.ExpProfileResponse;
import org.bodyguide_sv.activity.service.UserExpProfileService;
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
@RequestMapping("/exp")
@Tag(name = "Exp", description = "유저 레벨 관련")
public class ExpController {

    private final UserExpProfileService userExpProfileService;

    // 경험치 프로필 가져오기
    @GetMapping("/profile")
    @Operation(summary = "유저 레벨 프로필", description = "유저 레벨 정보")
    public ResponseEntity<ExpProfileResponse> getExpProfile(@AuthenticationPrincipal UserDetails userDetails) {

        ExpProfileResponse response = userExpProfileService.getExpProfileResponse(UUID.fromString(userDetails.getUsername()));

        return ResponseEntity.ok(response);
    }

}
