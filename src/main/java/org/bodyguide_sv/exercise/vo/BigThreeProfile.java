package org.bodyguide_sv.exercise.vo;

import java.time.LocalDate;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
public class BigThreeProfile {
    private Integer score;       // 점수
    private Double weight;       // 중량
    private Integer reps;        // 횟수
    private Double strength;     // Strength 지표
    private LocalDate lastUpdated; // 마지막 업데이트 일자
}
