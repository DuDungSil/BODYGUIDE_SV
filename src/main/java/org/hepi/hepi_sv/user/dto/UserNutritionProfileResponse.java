package org.hepi.hepi_sv.user.dto;


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
