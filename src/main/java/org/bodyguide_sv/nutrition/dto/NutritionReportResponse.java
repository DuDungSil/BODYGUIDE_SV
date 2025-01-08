package org.bodyguide_sv.nutrition.dto;

import java.util.List;

public record NutritionReportResponse(
    Double bmi,
    String intakeGoal,
    Double targetCalory,
    MealNutrientComposition composition,
    List<String> mealTimes,
    RecommendSource recommendSource
) {
    
}
