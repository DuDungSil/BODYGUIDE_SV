package org.bodyguide_sv.auth.service;

import java.util.UUID;

import org.bodyguide_sv.auth.controller.request.TokenRequest;
import org.bodyguide_sv.auth.controller.response.TokenResponse;
import org.bodyguide_sv.auth.exception.TokenException;
import org.bodyguide_sv.auth.jwt.TokenProvider;
import static org.bodyguide_sv.common.errorHandler.ErrorCode.INVALID_TOKEN;
import static org.bodyguide_sv.common.errorHandler.ErrorCode.TOKEN_EXPIRED;
import static org.bodyguide_sv.common.errorHandler.ErrorCode.TOKEN_MISMATCHED;
import static org.bodyguide_sv.common.errorHandler.ErrorCode.TOKEN_NOT_FOUND;
import org.bodyguide_sv.common.redis.entity.Token;
import org.bodyguide_sv.common.redis.repository.TokenRepository;
import org.bodyguide_sv.user.dto.UserDTO;
import org.bodyguide_sv.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TokenService {

    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final TokenProvider tokenProvider;

    // 액세스 토큰 검증
    public boolean validateAccessToken(String accessToken) {
        return tokenProvider.validateToken(accessToken);
    }

    // accessToken을 기반으로 Authentication 객체를 생성
    public Authentication getAuthentication(String accessToken) {
        return tokenProvider.getAuthenticationFromAccessToken(accessToken);
    }

    // 리프레시 토큰 저장 또는 갱신
    @Transactional
    public void saveOrUpdate(UUID userID, String refreshToken) {
        if (userID == null || !StringUtils.hasText(refreshToken)) {
            throw new IllegalArgumentException("User ID or refresh token is missing.");
        }

        tokenRepository.findById(userID).ifPresentOrElse(
            existingToken -> {
                existingToken.updateRefreshToken(refreshToken);
                tokenRepository.save(existingToken);
            },
            () -> {
                Token newToken = new Token(userID, refreshToken);
                tokenRepository.save(newToken);
            }
        );
    }

    // 리프레시 토큰 제거
    public void deleteRefreshToken(UUID userID) {
        if (userID == null) {
            throw new IllegalArgumentException("User ID is missing.");
        }
        tokenRepository.deleteById(userID);
    }

    // 소셜 인증 시 TokenResponseDTO 생성
    @Transactional
    public TokenResponse generateTokenResponse(Authentication authentication) {
        if (authentication == null) {
            throw new IllegalArgumentException("Authentication cannot be null.");
        }

        // JWT 액세스 및 리프레시 토큰 발급
        String accessToken = tokenProvider.generateAccessToken(authentication);
        String refreshToken = tokenProvider.generateRefreshToken(authentication);

        // Redis 저장
        saveOrUpdate(UUID.fromString(authentication.getName()), refreshToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    // TokenRequest를 이용한 토큰 재발급
    @Transactional
    public TokenResponse reissueTokenResponse(TokenRequest tokenRequest) {
        if (tokenRequest == null || !StringUtils.hasText(tokenRequest.refreshToken())) {
            throw new TokenException(INVALID_TOKEN);
        }

        String clientRefreshToken = tokenRequest.refreshToken();

        // 저장된 리프레시 토큰 확인
        UUID userID = UUID.fromString(tokenProvider.getAuthenticationFromRefreshToken(clientRefreshToken).getName());
        Token storedToken = tokenRepository.findById(userID)
                .orElseThrow(() -> new TokenException(TOKEN_NOT_FOUND));

        // 클라이언트와 저장된 리프레시 토큰 비교
        if (!storedToken.getRefreshToken().equals(clientRefreshToken)) {
            throw new TokenException(TOKEN_MISMATCHED);
        }

        // 리프레시 토큰 유효성 검증
        if (!tokenProvider.validateToken(clientRefreshToken)) {
            throw new TokenException(TOKEN_EXPIRED);
        }

        // 새로운 액세스 및 리프레시 토큰 발급
        String newAccessToken = tokenProvider.reissueAccessToken(clientRefreshToken);
        String newRefreshToken = tokenProvider.reissueRefreshToken(clientRefreshToken);

        // Redis 갱신
        saveOrUpdate(userID, newRefreshToken);

        log.info("Token reissued for userID: {}", userID);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
    
    // 유저 권한 상승 후 토큰 Response 반환
    @Transactional
    public TokenResponse upgradeUserRoleWithToken(UUID userID) {

        // 유저 권한 변경 
        UserDTO updatedUser = userService.upgradeUserRole(userID);

        // 새로운 토큰 발급
        Authentication authentication = tokenProvider.createAuthenticationFromUser(updatedUser);
        String newAccessToken = tokenProvider.generateAccessToken(authentication);
        String newRefreshToken = tokenProvider.generateRefreshToken(authentication);

        // 리프레시 토큰 저장/갱신
        saveOrUpdate(userID, newRefreshToken);

        return new TokenResponse(newAccessToken, newRefreshToken);
    }
    
    // userDTO로 토큰 Response 반환
    public TokenResponse generateTokenResponseByUserDTO(UserDTO userDTO) {

        Authentication authentication = tokenProvider.createAuthenticationFromUser(userDTO);
        String newAccessToken = tokenProvider.generateAccessToken(authentication);
        String newRefreshToken = tokenProvider.generateRefreshToken(authentication);
        
        return new TokenResponse(newAccessToken, newRefreshToken);
    }

    // userId로 액세스 토큰 생성
    @Transactional
    public String generateAccessTokenByUserId(UUID userId) {
        UserDTO userDTO = userService.getUserById(userId);
        Authentication authentication = tokenProvider.createAuthenticationFromUser(userDTO);
        return tokenProvider.generateAccessToken(authentication);
    }

    // 액세스토큰으로부터 userId 반환
    public UUID getUserIdFromAccessToken(String accessToken) {
        String sub = tokenProvider.getSubjectFromAccessToken(accessToken);
        return UUID.fromString(sub);
    }
}
