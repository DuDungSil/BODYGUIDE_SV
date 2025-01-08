package org.bodyguide_sv.nutrition.entity;

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
@Table(name = "NUTRIENT_INFO")
public class NutrientInfo {
    
    @Id
    @Column(name = "nutrient_id")
    private int id;

    @Column(name = "nutrient_name")
    private String name;

    @Column(name = "nutrient_function")
    private String function;

    @Column(name = "nutrient_intake")
    private String intake;

    @Column(name = "nutrient_side_effect")
    private String sideEffect;

    @Column(name = "nutrient_precaution")
    private String precaution;

    @Column(name = "nutrient_timing")
    private String timing;

    @Column(name = "nutrient_summary")
    private String summary;

}
