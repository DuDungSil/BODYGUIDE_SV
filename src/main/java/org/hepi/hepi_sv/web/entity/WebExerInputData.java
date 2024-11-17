package org.hepi.hepi_sv.web.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WEB_EXER_INPUT_DATA")
public class WebExerInputData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id")
    private Long id;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "bench_weight")
    private Double benchWeight;

    @Column(name = "bench_reps")
    private Integer benchReps;

    @Column(name = "squat_weight")
    private Double squatWeight;

    @Column(name = "squat_reps")
    private Integer squatReps;

    @Column(name = "dead_weight")
    private Double deadWeight;

    @Column(name = "dead_reps")
    private Integer deadReps;

    @Column(name = "overhead_weight")
    private Double overheadWeight;

    @Column(name = "overhead_reps")
    private Integer overheadReps;

    @Column(name = "pushup_reps")
    private Integer pushupReps;

    @Column(name = "pullup_reps")
    private Integer pullupReps;

    @Column(name = "exer_purposes", columnDefinition = "json")
    private String[] supplePurpose;

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "record_at")
    private LocalDateTime recordAt;
}
