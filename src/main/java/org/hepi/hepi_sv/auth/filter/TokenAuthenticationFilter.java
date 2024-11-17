package org.hepi.hepi_sv.auth.filter;

import org.hepi.hepi_sv.auth.exception.AuthException;
import org.hepi.hepi_sv.auth.jwt.TokenHeader;
import org.hepi.hepi_sv.auth.service.TokenService;
import static org.hepi.hepi_sv.common.errorHandler.ErrorCode.TOKEN_EXPIRED;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
                
        try {
            String accessToken = resolveToken(request);

            // accessToken 검증
            if (accessToken != null && tokenService.validateAccessToken(accessToken)) {
                setAuthentication(accessToken);
            }
        } catch (Exception e) {
            logger.error("Failed to set user authentication", e);
            throw new AuthException(TOKEN_EXPIRED);
        }

        filterChain.doFilter(request, response);
    }

    // 유효한 accessToken을 기반으로 Authentication 객체를 설정
    private void setAuthentication(String accessToken) {
        Authentication authentication = tokenService.getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
 
    // 요청에서 JWT 토큰을 추출
    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        if (ObjectUtils.isEmpty(token) || !token.startsWith(TokenHeader.TOKEN_PREFIX)) {
            return null;
        }
        return token.substring(TokenHeader.TOKEN_PREFIX.length());
    }
}
