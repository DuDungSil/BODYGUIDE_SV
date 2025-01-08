package org.bodyguide_sv.activity.entity;

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

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS_EXP_PROFILE")
public class UsersExpProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "current_level", columnDefinition = "TINYINT")
    private int currentLevel;

    @Column(name = "total_exp")
    private int totalExp;

    public void updateExperience(int newTotalExp, int newLevel) {
        this.totalExp = newTotalExp;
        this.currentLevel = newLevel;
    }

    // 생성 함수
    public static UsersExpProfile create(UUID userId) {
        return UsersExpProfile.builder()
                .userId(userId)
                .currentLevel(1) 
                .totalExp(0)    
                .build();
    }

}
