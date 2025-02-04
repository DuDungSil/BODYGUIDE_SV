package org.bodyguide_sv.web.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WEB_NUTRI_ANALYSIS_DATA")
public class WebNutriAnalysisData {

    @Id
    @Column(name = "data_id")
    private Long id; // 데이터 ID

    @Column(name = "bmi")
    private Double bmi; // 체질량지수

    @Column(name = "bmr")
    private Double bmr; // 기초대사량

    @Column(name = "tdee")
    private Double tdee; // 총 에너지 소비량

    @Column(name = "target_calory")
    private Double targetCalory; // 목표 칼로리
}