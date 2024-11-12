package org.hepi.hepi_sv.user.controller;

import java.util.UUID;

import org.hepi.hepi_sv.auth.dto.PrincipalDetails;
import org.hepi.hepi_sv.common.errorHandler.GlobalExceptionHandler;
import org.hepi.hepi_sv.user.dto.UserProfileDTO;
import org.hepi.hepi_sv.user.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserProfileService userProfileService;

    // 유저 프로필 입력 & 수정 ( 기본 정보 )

    // 유저 프로필 가져오기
    @GetMapping("/profile")
    public ResponseEntity<UserProfileDTO> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserProfileDTO userProfileDTO = userProfileService.getUserProfile(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(userProfileDTO);
    }

}
