package org.hepi.hepi_sv.user.dto;

import java.time.LocalDate;

public record UserProfileDTO(
    String gender,
    double height,
    double weight,
    LocalDate birthDate
) {
    
}
