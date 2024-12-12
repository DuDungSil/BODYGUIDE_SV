package org.hepi.hepi_sv.recommend.dto;

import java.util.List;

import org.hepi.hepi_sv.nutrition.dto.NutrientProfile;

public record PurposeNutrientProfiles(
    int purposeId,
    List<NutrientProfile> profiles 
) {
    
}
