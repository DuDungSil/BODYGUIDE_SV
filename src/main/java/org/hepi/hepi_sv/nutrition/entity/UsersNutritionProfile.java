package org.hepi.hepi_sv.nutrition.entity;

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
@Table(name = "USERS_NUTRITION_PROFILE")
public class UsersNutritionProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", updatable = false, nullable = false) 
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

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    // 생성 메서드
    public static UsersNutritionProfile create(UUID userId) {
        return UsersNutritionProfile.builder()
                .userId(userId)
                .pa(1) 
                .dietId(1)
                .targetWeight(null) 
                .targetCalory(null) 
                .carbCal(null)
                .proteinCal(null) 
                .fatCal(null) 
                .wakeupTime(null)
                .sleepTime(null) 
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
