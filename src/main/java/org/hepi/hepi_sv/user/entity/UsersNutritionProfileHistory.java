package org.hepi.hepi_sv.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS_NUTRITION_PROFILE_HISTORY")
public class UsersNutritionProfileHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id", updatable = false, nullable = false) 
    private Long id;

    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "PA", columnDefinition = "TINYINT")
    private int pa;

    @Column(name = "diet_type_id", columnDefinition = "TINYINT")
    private int dietId;

    @Column(name = "target_weight")
    private Double targetWeight;

    @Column(name = "target_calory")
    private Double targetCalory;

    @Column(name = "target_carbohydrate_calory")
    private Double carbCal;

    @Column(name = "target_protein_calory")
    private Double proteinCal;

    @Column(name = "target_fat_calory")
    private Double fatCal;

    @Column(name = "wakeup_time")
    private String wakeupTime;

    @Column(name = "sleep_time")
    private String sleepTime;

    @Column(name = "record_at")
    private LocalDateTime recordAt;

}
