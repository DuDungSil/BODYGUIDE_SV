package org.hepi.hepi_sv.auth.service;

import java.util.Collections;

import org.hepi.hepi_sv.auth.jwt.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

// 테스트 할때 쓰는 토큰 발급용
@Service
@RequiredArgsConstructor
public class TestTokenService {

    private final TokenProvider tokenProvider;

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