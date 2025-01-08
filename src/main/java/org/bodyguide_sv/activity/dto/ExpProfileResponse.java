package org.bodyguide_sv.activity.dto;

import lombok.Builder;

@Builder
public record ExpProfileResponse(
    int level,
    int currentExp,
    int requiredExp
) {
    
}
