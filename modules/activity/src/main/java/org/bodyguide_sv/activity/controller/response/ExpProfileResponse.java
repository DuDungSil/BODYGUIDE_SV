package org.bodyguide_sv.activity.controller.response;

import lombok.Builder;

@Builder
public record ExpProfileResponse(
    int level,
    int currentExp,
    int requiredExp
) {
    
}
