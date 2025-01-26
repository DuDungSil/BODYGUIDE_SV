package org.bodyguide_sv.weight.controller.request;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Schema(description = "체중 기록 Request")
public record WeightRecordRequest(
    @NotNull(message = "체중(weight)은 필수 값입니다.")
    @Positive(message = "체중(weight)은 0보다 커야 합니다.")
    Double weight,
            
    @NotNull(message = "기록 시간(recordAt)은 필수 값입니다.")
    LocalDateTime recordAt
) {
    
}
