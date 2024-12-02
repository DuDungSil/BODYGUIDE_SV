package org.hepi.hepi_sv.user.dto;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record UserProfileResponse(
    String nickname,
    String gender,
    double height,
    double weight,
    LocalDate birthDate,
    String profileImg,
    String introText
) {

}