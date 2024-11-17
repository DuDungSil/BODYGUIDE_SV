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
@Table(name = "exercise")
public class Exercise {
    
    @Id
    @Column(name = "exer_id")
    private long exerId;

    @Column(name = "exer_name")
    private String exerName;

    @Column(name = "exer_name_kr")
    private String exerNameKr;

    @Column(name = "exer_type")
    private String exerType;

    @Column(name = "body_part")
    private String bodyPart;

    @Column(name = "threshold_type")
    private int thresholdType;

}
