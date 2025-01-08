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
@Table(name = "RECOMMEND_EXERCISE_PURPOSE_NUTRIENT")
public class RecommendExercisePurposeNutrient {
    
    @Id
    @Column(name = "recommend_id")
    private int id;

    @Column(name = "purpose_id", columnDefinition = "SMALLINT")
    private int purposeId;

    @Column(name = "nutrient_id")
    private int nutrientId;

}
