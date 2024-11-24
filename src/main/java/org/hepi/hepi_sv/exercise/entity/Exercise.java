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
@Table(name = "EXERCISE")
public class Exercise {
    
    @Id
    @Column(name = "exer_id", columnDefinition = "SMALLINT")
    private int exerId;

    @Column(name = "exer_name")
    private String exerName;

    @Column(name = "exer_name_kr")
    private String exerNameKr;

    @Column(name = "exer_type", columnDefinition = "TINYINT")
    private int exerType;

    @Column(name = "muscle_id", columnDefinition = "SMALLINT")
    private int muscleId;

    @Column(name = "threshold_type", columnDefinition = "TINYINT")
    private int thresholdType;

}
