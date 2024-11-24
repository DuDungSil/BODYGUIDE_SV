package org.hepi.hepi_sv.nutrition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class NutrientProfile {
    int id;
    String name;
    String function;
    @JsonProperty("RDI")
    String RDI; // 권장 섭취량
    String sideEffect;
    String precaution;
    String timing;
    String summary;
    String mention;
}
