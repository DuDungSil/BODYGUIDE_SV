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
@Table(name = "EXERCISE_PURPOSE")
public class ExercisePurpose {
    
    @Id
    @Column(name = "purpose_id", columnDefinition = "SMALLINT")
    private int purposeId;

    @Column(name = "purpose")
    private String purpose;

}
