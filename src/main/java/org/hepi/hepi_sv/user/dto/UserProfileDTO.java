package org.hepi.hepi_sv.user.dto;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record UserProfileDTO(
    String nickname,
    String gender,
    int age,
    double height,
    double weight,
    LocalDate birth_date,
    String profile_img,
    String intro_text
) {

}