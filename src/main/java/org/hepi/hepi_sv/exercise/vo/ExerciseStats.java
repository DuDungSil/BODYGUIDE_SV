package org.hepi.hepi_sv.exercise.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Embeddable
public class ExerciseStats {

    @Column
    private Double benchWeight;   // 벤치프레스 중량
    private Integer benchReps;    // 벤치프레스 횟수

    private Double squatWeight;   // 스쿼트 중량
    private Integer squatReps;    // 스쿼트 횟수

    private Double deadWeight;    // 데드리프트 중량
    private Integer deadReps;     // 데드리프트 횟수

    private Double overheadWeight; // 오버헤드 프레스 중량
    private Integer overheadReps;  // 오버헤드 프레스 횟수

    private Integer pushupReps;   // 푸쉬업 횟수
    private Integer pullupReps;   // 풀업 횟수

    public void updateWith(ExerciseStats requestStats) {
        if (requestStats.getBenchWeight() != null)
            this.benchWeight = requestStats.getBenchWeight();
        if (requestStats.getBenchReps() != null && requestStats.getBenchReps() > 0)
            this.benchReps = requestStats.getBenchReps();

        if (requestStats.getSquatWeight() != null)
            this.squatWeight = requestStats.getSquatWeight();
        if (requestStats.getSquatReps() != null && requestStats.getSquatReps() > 0)
            this.squatReps = requestStats.getSquatReps();

        if (requestStats.getDeadWeight() != null)
            this.deadWeight = requestStats.getDeadWeight();
        if (requestStats.getDeadReps() != null && requestStats.getDeadReps() > 0)
            this.deadReps = requestStats.getDeadReps();

        if (requestStats.getOverheadWeight() != null)
            this.overheadWeight = requestStats.getOverheadWeight();
        if (requestStats.getOverheadReps() != null && requestStats.getOverheadReps() > 0)
            this.overheadReps = requestStats.getOverheadReps();

        if (requestStats.getPushupReps() != null && requestStats.getPushupReps() > 0)
            this.pushupReps = requestStats.getPushupReps();
        if (requestStats.getPullupReps() != null && requestStats.getPullupReps() > 0)
            this.pullupReps = requestStats.getPullupReps();
    }
    
}
