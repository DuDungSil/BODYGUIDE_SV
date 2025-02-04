package org.bodyguide_sv.nutrition.dto;

public record MealNutrientComposition(
    MealMacroNutrientDetails carbohydrate,
    MealMacroNutrientDetails protein,
    MealMacroNutrientDetails unFat,
    MealMacroNutrientDetails satFat
) {

}
