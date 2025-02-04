package org.bodyguide_sv.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WEB_EXER_ANALYSIS_DATA")
public class WebExerAnalysisData {

    @Id
    @Column(name = "data_id")
    private Long id; // 데이터 ID

    @Column(name = "total_score")
    private Double totalScore; // 총점

    @Column(name = "big_three")
    private Double bigThree; // 빅3 합산 점수

    @Column(name = "bench_score")
    private Double benchScore; // 벤치프레스 점수

    @Column(name = "bench_1rm")
    private Double bench1RM; // 벤치프레스 1RM

    @Column(name = "squat_score")
    private Double squatScore; // 스쿼트 점수

    @Column(name = "squat_1rm")
    private Double squat1RM; // 스쿼트 1RM

    @Column(name = "dead_score")
    private Double deadScore; // 데드리프트 점수

    @Column(name = "dead_1rm")
    private Double dead1RM; // 데드리프트 1RM

    @Column(name = "overhead_score")
    private Double overheadScore; // 오버헤드 점수

    @Column(name = "overhead_1rm")
    private Double overhead1RM; // 오버헤드 1RM

    @Column(name = "pushup_score")
    private Double pushupScore; // 푸쉬업 점수

    @Column(name = "pullup_score")
    private Double pullupScore; // 풀업 점수
}