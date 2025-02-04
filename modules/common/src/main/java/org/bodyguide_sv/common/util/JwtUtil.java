package org.bodyguide_sv.common.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.bodyguide_sv.common.enums.Role;
import static org.bodyguide_sv.common.errorHandler.ErrorCode.INVALID_JWT_SIGNATURE;
import static org.bodyguide_sv.common.errorHandler.ErrorCode.INVALID_TOKEN;
import org.bodyguide_sv.common.exception.TokenException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtUtil {

    @Value("${jwt.key}")
    private String key;

    private SecretKey secretKey;

    public static final String TOKEN_PREFIX = "Bearer ";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30L; // 30분
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7; // 7일
    private static final String KEY_ROLE = "role";

    // 초기화
    @PostConstruct
    public void init() {
        if (key.getBytes().length < 64) {
            throw new IllegalArgumentException("JWT Secret Key is too short. It must be at least 64 bytes long.");
        }
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    // 액세스 토큰 생성
    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME, "ACCESS");
    }

    // 리프레시 토큰 생성
    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME, "REFRESH");
    }

    // 액세스 토큰 검증
    public boolean validateAccessToken(String accessToken) {
        if (!StringUtils.hasText(accessToken)) {
            return false;
        }
        try {
            Claims claims = parseClaims(accessToken);

            // 먼저 토큰 타입 검증
            validateTokenType(claims, "ACCESS");

            // 그 후, 유효성 검사
            return validateToken(accessToken);
        } catch (TokenException e) {
            log.error("Invalid access token: {}", e.getMessage());
            return false;
        }
    }

    // 리프레시 토큰 검증
    public boolean validateRefreshToken(String refreshToken) {
        if (!StringUtils.hasText(refreshToken)) {
            return false;
        }
        try {
            Claims claims = parseClaims(refreshToken);

            // 먼저 토큰 타입 검증
            validateTokenType(claims, "REFRESH");

            // 그 후, 유효성 검사
            return validateToken(refreshToken);
        } catch (TokenException e) {
            log.error("Invalid refresh token: {}", e.getMessage());
            return false;
        }
    }

    // 토큰 생성 
    public String generateToken(Authentication authentication, long expireTime, String tokenType) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(KEY_ROLE, authorities)
                .claim("type", tokenType)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // 액세스 토큰 재발급
    public String reissueAccessToken(String refreshToken) {
        Authentication authentication = getAuthenticationFromRefreshToken(refreshToken);
        return generateAccessToken(authentication);
    }

    // 리프레시 토큰 재발급
    public String reissueRefreshToken(String refreshToken) {
        Authentication authentication = getAuthenticationFromRefreshToken(refreshToken);
        return generateRefreshToken(authentication);
    }

    // 토큰 검증
    private boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getExpiration().after(new Date());
        } catch (ExpiredJwtException e) {
            log.error("Token expired: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error("Token validation failed: {}", e.getMessage());
            return false;
        }
    }

    // 클레임 정보 파싱
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (MalformedJwtException e) {
            throw new TokenException(INVALID_TOKEN);
        } catch (SecurityException e) {
            throw new TokenException(INVALID_JWT_SIGNATURE);
        } catch (Exception e) {
            throw new TokenException(INVALID_TOKEN);
        }
    }

    // 토큰 타입 검증
    private void validateTokenType(Claims claims, String expectedType) {
        String tokenType = claims.get("type", String.class);
        if (!expectedType.equals(tokenType)) {
            throw new TokenException(INVALID_TOKEN);
        }
    }

    // 액세스토큰으로 인증 객체 생성
    public Authentication getAuthenticationFromAccessToken(String accessToken) {
        Claims claims = parseClaims(accessToken);

        // 액세스 토큰 검증
        validateTokenType(claims, "ACCESS");

        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
    }

    // 리프레시토큰으로 인증 객체 생성
    public Authentication getAuthenticationFromRefreshToken(String refreshToken) {
        Claims claims = parseClaims(refreshToken);

        // 리프레시 토큰 검증
        validateTokenType(claims, "REFRESH");

        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, refreshToken, authorities);
    }

    // 유저id, role 로 인증 객체 생성
    public Authentication createAuthenticationFromUser(UUID userId, Role userRole) {

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(userRole.getKey()));

        return new UsernamePasswordAuthenticationToken(
                userId.toString(),
                null,
                authorities);
    }

    // 권한 객체 목록으로 변환
    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Arrays.stream(claims.get(KEY_ROLE).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // 액세스토큰에서 sub 반환
    public String getSubjectFromAccessToken(String accessToken) {
        if (!StringUtils.hasText(accessToken)) {
            throw new TokenException(INVALID_TOKEN);
        }
        Claims claims = parseClaims(accessToken);

        // 액세스 토큰 검증
        validateTokenType(claims, "ACCESS");

        return claims.getSubject(); // sub 값 반환
    }

}
