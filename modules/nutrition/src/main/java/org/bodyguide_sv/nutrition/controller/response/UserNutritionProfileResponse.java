package org.bodyguide_sv.nutrition.controller.response;


public record UserNutritionProfileResponse(
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
