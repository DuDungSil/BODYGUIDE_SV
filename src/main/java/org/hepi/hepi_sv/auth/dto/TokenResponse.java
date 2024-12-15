package org.hepi.hepi_sv.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Access Token 및 Refresh Token 정보를 담은 Response")
public record TokenResponse(
        @Schema(description = "사용자 인증을 위한 Access Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String accessToken,

        @Schema(description = "Access Token 갱신을 위한 Refresh Token", example = "dGhpc0lzUmVmcmVzaFRva2Vu...")
        String refreshToken
) {

}