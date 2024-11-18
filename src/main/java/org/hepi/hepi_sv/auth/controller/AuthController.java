package org.hepi.hepi_sv.auth.controller;

import java.util.UUID;

import org.hepi.hepi_sv.auth.dto.TokenRequestDTO;
import org.hepi.hepi_sv.auth.dto.TokenResponseDTO;
import org.hepi.hepi_sv.auth.service.TestTokenService;
import org.hepi.hepi_sv.auth.service.TokenService;
import org.hepi.hepi_sv.auth.service.UnlinkService;
import org.hepi.hepi_sv.user.service.UserProviderTokenService;
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

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;
    private final UnlinkService unlinkService;
    private final UserProviderTokenService userProviderTokenService;
    private final TestTokenService testTokenService;

    // OAuth2 로그인 성공 후 콜백
    // @GetMapping("/success")
    // public ResponseEntity<TokenResponseDTO> loginSuccess(@Validated TokenResponseDTO tokenResponse) {
    //     return ResponseEntity.ok(tokenResponse);
    // }

    // 테스트 액세스 토큰 발급
    @GetMapping("/test")
    public ResponseEntity<String> generateTestAccessToken(@RequestParam(required = false) String param) {

        // 고정된 테스트 사용자 UUID
        String _testUser = "9609c827-2bd0-4c9b-8b19-06cb1169ea5c";

        // 액세스 토큰 생성
        String accessToken = testTokenService.generateTestAccessToken(_testUser, "ROLE_USER");

        // 액세스 토큰 반환
        return ResponseEntity.ok(accessToken);
    }
    
    // 로그아웃
    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails) {
        tokenService.deleteRefreshToken(UUID.fromString(userDetails.getUsername()));
        userProviderTokenService.deleteRefreshToken(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.noContent().build();
    }

    // 액세스토큰 재발급
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refreshToken(@RequestBody TokenRequestDTO tokenRequestDTO) {

        TokenResponseDTO tokenResponse = tokenService.reissueTokenResponse(tokenRequestDTO);

        return ResponseEntity.ok(tokenResponse);
    }

    // 회원탈퇴
    @GetMapping("/unlink")
    public ResponseEntity<String> unlink(@AuthenticationPrincipal UserDetails userDetails) {
        unlinkService.unlink(UUID.fromString(userDetails.getUsername()));
        tokenService.deleteRefreshToken(UUID.fromString(userDetails.getUsername()));
        userProviderTokenService.deleteRefreshToken(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok("성공");
    }

}