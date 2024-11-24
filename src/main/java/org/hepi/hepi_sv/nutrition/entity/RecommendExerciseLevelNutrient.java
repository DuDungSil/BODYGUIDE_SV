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
@Table(name = "RECOMMEND_EXERCISE_LEVEL_NUTRIENT")
public class RecommendExerciseLevelNutrient {
    
    @Id
    @Column(name = "recommend_id")
    private int id;

    @Column(name = "nutrient_id")
    private int nutrientId;

    @Column(name = "lvl")
    private int lvl;

    @Column(name = "mention")
    private String mention;

}
