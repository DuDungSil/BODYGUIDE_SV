package org.bodyguide_sv.web.dto.nutrition;

import java.util.List;

import org.bodyguide_sv.nutrition.dto.MealNutrientComposition;
import org.bodyguide_sv.nutrition.dto.RecommendSource;

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
    private RecommendSource sources;
    private WebRecommendFood products;
}

