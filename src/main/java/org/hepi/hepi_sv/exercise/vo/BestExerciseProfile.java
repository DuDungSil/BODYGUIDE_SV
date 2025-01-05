package org.hepi.hepi_sv.exercise.vo;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
public class BestExerciseProfile {
    private Integer exerciseId;         // 운동 ID
    private Integer score;           // 점수 (120점 넘을 수 있음)
    private Double strength;         // Strength 지표
    private LocalDate lastUpdated;   // 마지막 업데이트 일자
}
