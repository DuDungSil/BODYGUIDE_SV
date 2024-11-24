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
@Table(name = "EXERCISE_THRESHOLD")
public class ExerciseThreshold {
    
    @Id
    @Column(name = "threshold_id")
    private int thresholdId;

    @Column(name = "exer_id", columnDefinition = "SMALLINT")
    private int exerId;

    @Column(name = "gender")
    private String gender;

    @Column(name = "rank")
    private int rank;

    @Column(name = "threshold")
    private Double threshold;

}
