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
@Table(name = "body_part")
public class BodyPart {
    
    @Id
    @Column(name = "part_id")
    private long partId;

    @Column(name = "part_name")
    private String partName;

    @Column(name = "part_name_kr")
    private String partNameKr;

    @Column(name = "muscle_group")
    private String muscleGroup;

}
