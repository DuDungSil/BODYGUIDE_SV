package org.hepi.hepi_sv.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "RECOMMEND_FOOD")
public class RecommendFood {
    
    @Id
    @Column(name = "recommend_id")
    private int id;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "nutrient_type", columnDefinition = "TINYINT")
    private int nutrientType;

    @Column(name = "diet_type", columnDefinition = "TINYINT")
    private int dietType;

}
