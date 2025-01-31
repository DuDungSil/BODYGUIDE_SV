package org.bodyguide_sv.auth.controller;

import java.util.UUID;

import org.bodyguide_sv.auth.controller.request.InitializeRequest;
import org.bodyguide_sv.auth.controller.request.TokenRequest;
import org.bodyguide_sv.auth.controller.response.TokenResponse;
import org.bodyguide_sv.auth.service.InitializeService;
import org.bodyguide_sv.auth.service.LogoutService;
import org.bodyguide_sv.auth.service.OAuthCallbackService;
import org.bodyguide_sv.auth.service.RecoveryAccountService;
import org.bodyguide_sv.auth.service.RecoveryCallbackService;
import org.bodyguide_sv.auth.service.RecreateAccountService;
import org.bodyguide_sv.auth.service.RefreshService;
import org.bodyguide_sv.auth.service.TestTokenService;
import org.bodyguide_sv.auth.service.UnlinkService;
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

    private final RecoveryAccountService recoveryAccountService;
    private final RecreateAccountService recreateAccountService;
    private final InitializeService initializeService;
    private final LogoutService logoutService;
    private final RefreshService refreshService;
    private final UnlinkService unlinkService;
    private final OAuthCallbackService oAuthCallbackService;
    private final RecoveryCallbackService recoveryCallbackService;

    @GetMapping("/test")
    @Operation(summary = "테스트용 액세스 토큰 발급 ( 인증 X )", description = "테스트용 액세스 토큰 발급")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> generateTestAccessToken() {
        String accessToken = testTokenService.getTestAccessToken();
        return ResponseEntity.ok(accessToken);
    }

    @GetMapping("/callback")
    @Operation(summary = "OAuth 소셜 로그인 성공시 callback", description = "OAuth 소셜 로그인 성공시 액세스토큰, 리프레시토큰 응답")
    public void handleOAuthCallback(
            @RequestParam("access_token") String accessToken,
            @RequestParam("refresh_token") String refreshToken,
            HttpServletResponse response
    ) {
        oAuthCallbackService.processCallback(accessToken, refreshToken, response);
    }

    @GetMapping("/callback/recovery")
    @Operation(summary = "OAuth 소셜 로그인 계정 복구 callback", description = "계정 복구시 액세스토큰 응답")
    public void handleOAuthRecoveryCallback(
            @RequestParam("access_token") String accessToken,
            HttpServletResponse response
    ) {
        recoveryCallbackService.processCallback(accessToken, response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "토큰 재발급 ( 인증 X )", description = "클라이언트로부터 리프레시 토큰을 전달받아 유저 리프레시 토큰 저장소에서 검증 후 새로운 액세스 토큰, 리프레시 토큰을 재발급")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody TokenRequest request) {
        TokenResponse response = refreshService.tokenRefresh(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/initialize")
    @Operation(summary = "GUEST 권한 계정 초기화 ( 온보딩 )", description = "계정 초기 데이터를 입력받아 계정 프로필을 초기화 후 GUEST -> USER 권한 상승")
    public ResponseEntity<TokenResponse> initialize(@AuthenticationPrincipal UserDetails userDetails,
        @Valid @RequestBody InitializeRequest request) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        TokenResponse response =  initializeService.initialize(userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/logout")
    @Operation(summary = "로그아웃", description = "유저 리프레시 토큰 저장소의 리프레시 토큰을 제거")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        logoutService.logout(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/unlink")
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴")
    public ResponseEntity<String> unlink(@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        unlinkService.unlink(userId);
        return ResponseEntity.ok("회원 탈퇴 성공");
    }

    @GetMapping("/recovery/confirm")
    @Operation(summary = "계정 복구 동의", description = "이전 계정으로 복구 한 후 TokenResponse 반환")
    public ResponseEntity<TokenResponse> accountRecovery(@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        TokenResponse response = recoveryAccountService.recoveryAccount(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recovery/reject")
    @Operation(summary = "계정 복구 거부 ( 새로운 계정 생성 )", description = "새로운 계정 생성 후 재 로그인")
    public ResponseEntity<String> accountCreate(@AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        recreateAccountService.recreateAccount(userId);
        return ResponseEntity.ok("기존 계정 삭제 완료. 다시 로그인해 주세요");
    }
}