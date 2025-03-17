package org.bodyguide_sv.auth.service;

import java.util.Collections;

import org.bodyguide_sv.common.util.JwtUtil;
import org.bodyguide_sv.user.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

// 테스트 할때 쓰는 토큰 발급용
@Service
@RequiredArgsConstructor
public class TestTokenService {

    private final JwtUtil tokenProvider;
    private final UserService userService;

    public String getTestAccessToken() {

        // 테스트 유저 조회
        // String _testUser = userService.getTestUserUUID();
        String _testUser = "532e891d-c4da-484f-a48d-8d29b7821a5d";

        // 액세스 토큰 반환
        return generateTestAccessToken(_testUser, "ROLE_USER");

    }

    /**
     * Generate a test access token.
     *
     * @param username the username for the token
     * @param role the role for the token
     * @return the generated access token
     */
    public String generateTestAccessToken(String username, String role) {
        User user = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority(role)));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
        return tokenProvider.generateToken(authentication, 1000L * 60 * 60 * 24 * 1, "ACCESS"); // 1 days in milliseconds
    }

    /**
     * Generate a test refresh token.
     *
     * @param username the username for the token
     * @param role the role for the token
     * @return the generated refresh token
     */
    public String generateTestRefreshToken(String username, String role) {
        User user = new User(username, "", Collections.singletonList(new SimpleGrantedAuthority(role)));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
        return tokenProvider.generateRefreshToken(authentication);
    }
}
