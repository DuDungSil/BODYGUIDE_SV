package org.hepi.hepi_sv.auth.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "GUEST 권한 계정 초기화 Request")
public record InitializeRequest(
    @NotBlank(message = "nickname 필수 입력")
    @Size(max = 10, message = "nickname은 10자를 초과할 수 없습니다.")
    @Schema(description = "닉네임", example = "헬창이윤구")
    String nickname,
                    
    @NotBlank(message = "성별 필수 입력")
    @Pattern(regexp = "M|F", message = "성별은 'M' 또는 'F'만 가능합니다.")
    @Schema(description = "성별", example = "M or F")
    String gender,
                    
    @DecimalMin(value = "10", message = "키 값은 최소 10 이상이어야 합니다.")
    @DecimalMax(value = "250", message = "키 값은 최대 250 이하여야 합니다.")
    @Schema(description = "키", example = "180")
    double height,
                    
    @DecimalMin(value = "10", message = "몸무게 값은 최소 10 이상이어야 합니다.")
    @DecimalMax(value = "300", message = "몸무게 값은 최대 300 이하여야 합니다.")
    @Schema(description = "몸무게", example = "80")
    double weight,
                    
    @NotNull(message = "생년월일 필수 입력")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(description = "생년월일", example = "1999-03-01")
    LocalDate birthDate,
                    
    @Schema(description = "추천경로", example = "인터넷 검색")
    String Source
) {
    
}
