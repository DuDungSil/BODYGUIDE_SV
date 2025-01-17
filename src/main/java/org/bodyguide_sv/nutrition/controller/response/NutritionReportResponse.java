package org.bodyguide_sv.nutrition.controller.response;

import java.util.List;

import org.bodyguide_sv.nutrition.dto.MealNutrientComposition;
import org.bodyguide_sv.nutrition.dto.RecommendSource;

public record NutritionReportResponse(
    Double bmi,
    String intakeGoal,
    Double targetCalory,
    MealNutrientComposition composition,
    List<String> mealTimes,
    RecommendSource recommendSource
) {
    
}
