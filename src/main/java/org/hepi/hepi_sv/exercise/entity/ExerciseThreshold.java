package org.hepi.hepi_sv.exercise.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "exercise_threshold")
public class ExerciseThreshold {
    
    @Id
    @Column(name = "threshold_id")
    private long thresholdId;

    @Column(name = "exer_name")
    private String exerName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "rank")
    private int rank;

    @Column(name = "threshold")
    private Double threshold;

}
