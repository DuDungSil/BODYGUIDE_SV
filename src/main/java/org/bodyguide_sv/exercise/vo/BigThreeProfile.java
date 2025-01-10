package org.bodyguide_sv.exercise.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Embeddable
public class BigThreeProfile {
    private Double score;       // 점수
    private Double weight;       // 중량
    private Integer reps;        // 횟수
    private LocalDateTime updatedAt; // 마지막 업데이트 일자

    public static BigThreeProfile createDefault() {
        return BigThreeProfile.builder()
                .score(0.0)
                .weight(0.0)
                .reps(0)
                .updatedAt(LocalDateTime.now())
                .build();
    }
    
}
