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
public class MuscleScoreProfile {
    private Integer exerciseId;         // 운동 ID
    private Double score;           // 점수 (120점 넘을 수 있음)
    private LocalDateTime updatedAt; // 마지막 업데이트 일자
    
    public static MuscleScoreProfile createDefault() {
        return MuscleScoreProfile.builder()
            .exerciseId(null)
            .score(0.0)
            .updatedAt(LocalDateTime.now())
            .build();
    }
}
