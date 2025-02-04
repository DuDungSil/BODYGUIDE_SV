package org.bodyguide_sv.web.dto.exercise;

import java.util.List;

import org.bodyguide_sv.nutrition.dto.NutrientProfile;

import lombok.Data;

@Data
public class WebPurposeRecommend{
    String purpose;
    List<NutrientProfile> profiles;
}
