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
@Table(name = "LEVEL_RECOMMEND_NUTRITION")
public class LevelRecommendNutrition {
    
    @Id
    @Column(name = "nutrition_name")
    private String nutritionName;

    @Column(name = "lvl")
    private int lvl;

    @Column(name = "mention")
    private String mention;

}
