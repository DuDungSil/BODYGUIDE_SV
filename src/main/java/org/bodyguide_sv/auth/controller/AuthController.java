package org.bodyguide_sv.auth.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.bodyguide_sv.auth.dto.InitializeRequest;
import org.bodyguide_sv.auth.dto.TokenRequest;
import org.bodyguide_sv.auth.dto.TokenResponse;
import org.bodyguide_sv.auth.service.TestTokenService;
import org.bodyguide_sv.auth.service.TokenService;
import org.bodyguide_sv.auth.service.UnlinkService;
import org.bodyguide_sv.user.service.UserMetaService;
import org.bodyguide_sv.user.service.UserProfileService;
import org.bodyguide_sv.user.service.UserService;
import org.bodyguide_sv.user.service.UserSocialTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "인증 관련")
public class AuthController {

    private final TestTokenService testTokenService; // 개발용

    private final TokenService tokenService;
    private final UnlinkService unlinkService;
    private final UserSocialTokenService userProviderTokenService;
    private final UserProfileService userProfileService;
    private final UserService userService;
    private final UserMetaService userMetaService;

    @GetMapping("/callback")
    public void handleOAuthCallback(
            @RequestParam("access_token") String accessToken,
            @RequestParam("refresh_token") String refreshToken,
            HttpServletResponse response
    ) {
        try {
            // JSON 데이터 생성
            String jsonPayload = String.format(
                "{\"accessToken\":\"%s\", \"refreshToken\":\"%s\"}",
                accessToken,
                refreshToken
            );
    
            // 앱으로 리디렉션 URL 생성
            String redirectUri = "bodyguide://oauth2redirect?jsonPayload=" +
                    URLEncoder.encode(jsonPayload, StandardCharsets.UTF_8);
    
            // 로직 처리 (예: 사용자 정보 업데이트, 토큰 저장 등)
            //userMetaService.updateLastLoginAt(accessToken);
    
            // 앱으로 리디렉션
            response.sendRedirect(redirectUri);
    
        } catch (Exception e) {
            try {
                // 오류 발생 시 앱으로 오류를 전달하거나 적절한 응답 반환
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "오류 발생: " + e.getMessage());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @GetMapping("/test")
    @Operation(summary = "테스트용 액세스 토큰 발급 ( 인증 X )", description = "테스트용 액세스 토큰 발급")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> generateTestAccessToken() {

        String _testUser = userService.getTestUserUUID();

        // 액세스 토큰 생성
        String accessToken = testTokenService.generateTestAccessToken(_testUser, "ROLE_USER");

        // 액세스 토큰 반환
        return ResponseEntity.ok(accessToken);
    }

    @PostMapping("/initialize")
    @Operation(summary = "GUEST 권한 계정 초기화", description = "계정 초기 데이터를 입력받아 계정 프로필을 초기화 후 GUEST -> USER 권한 상승")
    public ResponseEntity<TokenResponse> initialize(@AuthenticationPrincipal UserDetails userDetails,
        @Valid @RequestBody InitializeRequest request) {

        // 프로필 입력
        userProfileService.initializeUserProfile(UUID.fromString(userDetails.getUsername()), request);

        // 유저 권한 상승 후 새 토큰 반환
        TokenResponse tokenResponse = tokenService.upgradeUserRoleWithToken(UUID.fromString(userDetails.getUsername()));

        return ResponseEntity.ok(tokenResponse);
    }

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃", description = "유저 리프레시 토큰 저장소의 리프레시 토큰을 제거")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {
        tokenService.deleteRefreshToken(UUID.fromString(userDetails.getUsername()));
        userProviderTokenService.deleteRefreshToken(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급 ( 인증 X )", description = "클라이언트로부터 리프레시 토큰을 전달받아 유저 리프레시 토큰 저장소에서 검증 후 새로운 액세스 토큰, 리프레시 토큰을 재발급")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody TokenRequest tokenRequest) {

        TokenResponse tokenResponse = tokenService.reissueTokenResponse(tokenRequest);

        return ResponseEntity.ok(tokenResponse);
    }

    @GetMapping("/unlink")
    @Operation(summary = "회원 탈퇴", description = "미완성")
    public ResponseEntity<String> unlink(@AuthenticationPrincipal UserDetails userDetails) {
        unlinkService.unlink(UUID.fromString(userDetails.getUsername()));
        tokenService.deleteRefreshToken(UUID.fromString(userDetails.getUsername()));
        userProviderTokenService.deleteRefreshToken(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok("성공");
    }

}