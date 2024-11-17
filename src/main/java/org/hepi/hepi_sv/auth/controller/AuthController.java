package org.hepi.hepi_sv.auth.controller;

import java.util.UUID;

import org.hepi.hepi_sv.auth.dto.TokenRequestDTO;
import org.hepi.hepi_sv.auth.dto.TokenResponseDTO;
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
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final TokenService tokenService;
    private final UnlinkService unlinkService;
    private final UserProviderTokenService userProviderTokenService;

    // OAuth2 로그인 성공 후 콜백
    // @GetMapping("/success")
    // public ResponseEntity<TokenResponseDTO> loginSuccess(@Validated TokenResponseDTO tokenResponse) {
    //     return ResponseEntity.ok(tokenResponse);
    // }

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