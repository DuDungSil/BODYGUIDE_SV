package org.bodyguide_sv.activity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "LEVEL_INFO")
public class LevelInfo {

    @Id
    @Column(name = "level")
    private int level;

    @Column(name = "next_level_exp")
    private int nextLevelExp;

    @Column(name = "total_exp")
    private int totalExp;

    @Column(name = "discount_rate")
    private Double discountRate;

}
