package org.hepi.hepi_sv.auth.controller;

import java.util.UUID;

import org.hepi.hepi_sv.auth.dto.InitializeRequest;
import org.hepi.hepi_sv.auth.dto.TokenRequest;
import org.hepi.hepi_sv.auth.dto.TokenResponse;
import org.hepi.hepi_sv.auth.service.TestTokenService;
import org.hepi.hepi_sv.auth.service.TokenService;
import org.hepi.hepi_sv.auth.service.UnlinkService;
import org.hepi.hepi_sv.user.service.UserProfileService;
import org.hepi.hepi_sv.user.service.UserSocialTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    // OAuth2 로그인 성공 후 콜백
    // @GetMapping("/success")
    // public ResponseEntity<TokenResponseDTO> loginSuccess(@Validated TokenResponseDTO tokenResponse) {
    //     return ResponseEntity.ok(tokenResponse);
    // }

    @GetMapping("/test")
    @Operation(summary = "테스트용 액세스 토큰 발급 ( 인증 X )", description = "테스트용 액세스 토큰 발급")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<String> generateTestAccessToken() {

        // 고정된 테스트 사용자 UUID
        String _testUser = "9609c827-2bd0-4c9b-8b19-06cb1169ea5c";

        // 액세스 토큰 생성
        String accessToken = testTokenService.generateTestAccessToken(_testUser, "ROLE_USER");

        // 액세스 토큰 반환
        return ResponseEntity.ok(accessToken);
    }
    
    @PostMapping("/initialize")
    @Operation(summary = "계정 초기화", description = "계정 초기 데이터를 입력받아 계정 프로필을 초기화 후 GUEST -> USER 권한 상승")
    public ResponseEntity<TokenResponse> initialize(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody InitializeRequest request) {
        
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
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenRequest tokenRequestDTO) {

        TokenResponse tokenResponse = tokenService.reissueTokenResponse(tokenRequestDTO);

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