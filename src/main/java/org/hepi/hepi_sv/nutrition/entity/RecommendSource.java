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
@Table(name = "RECOMMEND_SOURCE")
public class RecommendSource {
    
    @Id
    @Column(name = "source")
    private String source;

    @Column(name = "nutrient_type")
    private String nutrientType;

    @Column(name = "diet_type")
    private String dietType;

}
