package org.bodyguide_sv.nutrition.dto;

public record UserNutritionProfileDTO(
    int pa,
    int dietId,      
    Double targetWeight,
    Double targetCalory,
    Double carbCal,
    Double proteinCal,
    Double fatCal,
    String wakeupTime,
    String sleepTime
) {
    
}
