package org.bodyguide_sv.calendar.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "USERS_INTAKE_HISTORY")
public class UsersIntakeHistory {

    @Id
    @Column(name = "history_id")
    private Long historyId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "record_at")
    private LocalDateTime recordAt;

    @Column(name = "intake_date")
    private LocalDate intakeDate;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "brand")
    private String brand;

    @Column(name = "serving_amount")
    private Double servingAmount;

    @Column(name = "serving_unit")
    private String servingUnit;

    @Column(name = "calory")
    private Double calory;

    @Column(name = "carbohydrate")
    private Double carbohydrate;

    @Column(name = "sugar")
    private Double sugar;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "fat")
    private Double fat;

    @Column(name = "sodium")
    private Double sodium;

    @Column(name = "cholesterol")
    private Double cholesterol;

    @Column(name = "saturated_fat")
    private Double saturatedFat;

    @Column(name = "unsaturated_fat")
    private Double unsaturatedFat;

    @Column(name = "trans_fat")
    private Double transFat;

    @Column(name = "dietary_fiber")
    private Double dietaryFiber;

    // Getters and Setters (Optional if using a framework like Lombok)
}
