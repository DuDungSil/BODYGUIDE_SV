package org.hepi.hepi_sv.web.dto.nutrition;

import java.util.List;

import org.hepi.hepi_sv.nutrition.dto.MealNutrientComposition;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WebNutrientResult {
    @JsonProperty("BMI")
    private Double BMI;
    @JsonProperty("BMR")
    private Double BMR;
    @JsonProperty("TDEE")
    private Double TDEE;
    private Double targetCalory;
    private String dietGoal;
    private MealNutrientComposition composition;
    private String wakeup;
    private String sleep;
    private List<String> mealTimes;
    private RecommendSourceDto sources;
    private RecommendProduct products;
}

