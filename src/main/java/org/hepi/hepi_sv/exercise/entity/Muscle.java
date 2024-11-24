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
@Table(name = "MUSCLE")
public class Muscle {
    
    @Id
    @Column(name = "muscle_id", columnDefinition = "SMALLINT")
    private int muscleId;

    @Column(name = "name")
    private String name;

    @Column(name = "name_kr")
    private String nameKr;

    @Column(name = "muscle_group_id", columnDefinition = "TINYINT")
    private int muscleGroupId;

}
