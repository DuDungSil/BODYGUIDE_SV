package org.bodyguide_sv.user.dto;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "유저 프로필 초기화 DTO")
public record InitializeProfileDTO(
    String nickname,
    String gender,
    double height,
    double weight,
    LocalDate birthDate,
    String source
) {}
