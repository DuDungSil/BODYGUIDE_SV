package org.bodyguide_sv.auth.jwt;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.bodyguide_sv.auth.exception.TokenException;
import static org.bodyguide_sv.common.errorHandler.ErrorCode.INVALID_JWT_SIGNATURE;
import static org.bodyguide_sv.common.errorHandler.ErrorCode.INVALID_TOKEN;
import static org.bodyguide_sv.common.errorHandler.ErrorCode.USER_NOT_FOUND;
import org.bodyguide_sv.user.Exception.UserException;
import org.bodyguide_sv.user.entity.Users;
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
public class TokenProvider {

    @Value("${jwt.key}")
    private String key;

    private SecretKey secretKey;

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 1L; // 1분
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
    public boolean validateToken(String token) {
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

    // 유저 엔티티로 인증 객체 생성
    public Authentication createAuthenticationFromUser(Users user) {
        if (user == null) {
            throw new UserException(USER_NOT_FOUND);
        }
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));

        return new UsernamePasswordAuthenticationToken(
                user.getUserId().toString(),
                null,
                authorities);
    }

    // 권한 객체 목록으로 변환
    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        return Arrays.stream(claims.get(KEY_ROLE).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
