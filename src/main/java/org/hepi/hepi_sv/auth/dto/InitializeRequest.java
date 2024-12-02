package org.hepi.hepi_sv.auth.dto;

import java.time.LocalDate;

public record InitializeRequest(
    String nickname,
    String gender,
    double height,
    double weight,
    LocalDate birthDate,
    String Source
) {
    
}
