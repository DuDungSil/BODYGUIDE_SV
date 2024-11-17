package org.hepi.hepi_sv.exercise.dto;

import java.util.List;

import org.hepi.hepi_sv.nutrition.dto.NutrientProfile;

import lombok.Data;

@Data
public class PurposeRecommend{
    String purpose;
    List<NutrientProfile> profiles;
}
