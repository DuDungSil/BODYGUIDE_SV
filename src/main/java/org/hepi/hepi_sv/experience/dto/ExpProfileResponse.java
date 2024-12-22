package org.hepi.hepi_sv.experience.dto;

import lombok.Builder;

@Builder
public record ExpProfileResponse(
    int level,
    int currentExp,
    int requiredExp
) {
    
}
