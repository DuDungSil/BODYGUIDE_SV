package org.hepi.hepi_sv.auth.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken
) {

    public TokenResponse {
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalArgumentException("accessToken cannot be blank");
        }
    }
}
