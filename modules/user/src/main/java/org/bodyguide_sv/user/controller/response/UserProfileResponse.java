package org.bodyguide_sv.user.controller.response;

import java.time.LocalDate;

import lombok.Builder;

@Builder
public record UserProfileResponse(
        String nickname,
        String gender,
        double height,
        double weight,
        LocalDate birthDate,
        LocalDate registerDate,
        String profileImg,
        String introText) {

}
