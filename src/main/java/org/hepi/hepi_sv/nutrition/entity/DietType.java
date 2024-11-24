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
@Table(name = "DIET_TYPE")
public class DietType {
    
    @Id
    @Column(name = "diet_id", columnDefinition = "TINYINT")
    private int dietId;

    @Column(name = "diet_name")
    private String dietName;

    @Column(name = "carbohydrate")
    private Double carbohydrate;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "un_fat")
    private Double unFat;

    @Column(name = "sat_fat")
    private Double satFat;
    
}
