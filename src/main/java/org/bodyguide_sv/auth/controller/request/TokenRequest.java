package org.bodyguide_sv.auth.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Refresh Token 정보를 담은 Request")
public record TokenRequest(
    @NotBlank(message = "Refresh Token은 필수입니다.")
    @Size(max = 1000, message = "Refresh Token은 1000자를 초과할 수 없습니다.")
    @Schema(description = "Access Token 갱신을 위한 Refresh Token", example = "dGhpc0lzUmVmcmVzaFRva2Vu...")
    String refreshToken
) {
    
}
