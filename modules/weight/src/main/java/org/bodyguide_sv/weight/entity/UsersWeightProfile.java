package org.bodyguide_sv.weight.entity;

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

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS_WEIGHT_PROFILE")
public class UsersWeightProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "record_date")
    private LocalDateTime recordDate;

    public void updateWeight(Double weight, LocalDateTime recordDate) {
        this.weight = weight;
        this.recordDate = recordDate;
    }

    public static UsersWeightProfile create(UUID userId) {
        return UsersWeightProfile.builder()
                                    .userId(userId)
                                    .weight(null)
                                    .recordDate(null)
                                    .build();
    }

}
