package org.bodyguide_sv.weight.dto;

import java.time.LocalDateTime;

public record UserWeightProfileDTO(
    Double weight,
    LocalDateTime recordDate
) {
    
}
