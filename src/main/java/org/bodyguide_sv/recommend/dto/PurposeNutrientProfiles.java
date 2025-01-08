package org.bodyguide_sv.recommend.dto;

import java.util.List;

import org.bodyguide_sv.nutrition.dto.NutrientProfile;

public record PurposeNutrientProfiles(
    int purposeId,
    List<NutrientProfile> profiles 
) {
    
}
