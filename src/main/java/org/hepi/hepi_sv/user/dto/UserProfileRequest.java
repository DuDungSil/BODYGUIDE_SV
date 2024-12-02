package org.hepi.hepi_sv.user.dto;

import java.time.LocalDate;

public record UserProfileRequest(
    String nickname,
    String gender,
    double height,
    double weight,
    LocalDate birthDate,
    String profileImg,
    String introText
) {
    
}
