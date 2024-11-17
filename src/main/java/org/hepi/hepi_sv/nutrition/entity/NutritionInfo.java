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
@Table(name = "NUTRITION_INFO")
public class NutritionInfo {
    
    @Id
    @Column(name = "nutrition_name")
    private String nutritionName;

    @Column(name = "nutrition_function")
    private String nutritionFunction;

    @Column(name = "nutrition_intake")
    private String nutritionIntake;

    @Column(name = "nutrition_side_effect")
    private String nutritionSideEffect;

    @Column(name = "nutrition_precaution")
    private String nutritionPrecaution;

    @Column(name = "nutrition_timing")
    private String nutritionTiming;

    @Column(name = "nutrition_summary")
    private String nutritionSummary;

}
