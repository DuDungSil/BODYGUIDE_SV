package org.hepi.hepi_sv.activity.dto;

import lombok.Builder;

@Builder
public record ExpProfileResponse(
    int level,
    int currentExp,
    int requiredExp
) {
    
}
