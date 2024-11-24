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
@Table(name = "EXERCISE_TYPE")
public class ExerciseType {
    
    @Id
    @Column(name = "type_id", columnDefinition = "TINYINT")
    private long typeId;

    @Column(name = "type")
    private String type;

    @Column(name = "type_kr")
    private String typeKr;

}
