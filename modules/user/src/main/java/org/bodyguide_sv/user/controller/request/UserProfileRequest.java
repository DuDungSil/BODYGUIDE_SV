package org.bodyguide_sv.user.controller.request;

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
