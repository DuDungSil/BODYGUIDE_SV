package org.hepi.hepi_sv.nutrition.dto;

import lombok.Data;

@Data
public class MealNutrientComposition {
    MealMacroDetails carbohydrate;
    MealMacroDetails protein;
    MealMacroDetails unFat;
    MealMacroDetails satFat;

    public MealNutrientComposition(MealMacroDetails carbohydrate_, MealMacroDetails protein_, MealMacroDetails unFat_ , MealMacroDetails satFat_){
        this.carbohydrate = carbohydrate_;
        this.protein = protein_;
        this.unFat = unFat_;
        this.satFat = satFat_;
    }
}
