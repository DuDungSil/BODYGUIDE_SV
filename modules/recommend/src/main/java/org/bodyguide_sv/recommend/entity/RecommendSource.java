package org.bodyguide_sv.recommend.entity;

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
    @Column(name = "recommend_id")
    private int id;

    @Column(name = "source")
    private String source;

    @Column(name = "nutrient_type", columnDefinition = "TINYINT")
    private int nutrientTypeId;

    @Column(name = "diet_type", columnDefinition = "TINYINT")
    private int dietTypeId;

}
