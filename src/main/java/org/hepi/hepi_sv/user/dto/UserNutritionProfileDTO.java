package org.hepi.hepi_sv.user.dto;

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
