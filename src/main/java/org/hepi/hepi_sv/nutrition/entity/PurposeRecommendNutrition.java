package org.hepi.hepi_sv.nutrition.entity;

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
@Table(name = "EXERCISE_PURPOSE_NUTRITION_REL")
public class PurposeRecommendNutrition {
    
    @Id
    @Column(name = "rel_id")
    private String rel_id;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "nutrition")
    private String nutrition;

}
