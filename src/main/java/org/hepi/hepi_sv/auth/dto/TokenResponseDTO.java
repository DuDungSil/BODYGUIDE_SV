package org.hepi.hepi_sv.auth.dto;

public record TokenResponseDTO(String accessToken, String refreshToken) {

    public TokenResponseDTO {
        if (accessToken == null || accessToken.isBlank()) {
            throw new IllegalArgumentException("accessToken cannot be blank");
        }
    }
}
