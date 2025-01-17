package org.bodyguide_sv.exercise.entity;

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
@Table(name = "USERS_EXERCISE_VOLUME_WEEKLY")
public class UsersExerciseVolumeWeekly {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false) 
    private Long id;

    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "year" , columnDefinition = "TINYINT")
    private Integer year;

    @Column(name = "week" , columnDefinition = "TINYINT")
    private Integer week;

    @Column(name = "volume")
    private double volume;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateVolume(double volume) {
        this.volume = volume;
        this.updatedAt = LocalDateTime.now();
    }

    public static UsersExerciseVolumeWeekly create(UUID userId, int year, int week){
        return UsersExerciseVolumeWeekly.builder()
                                        .userId(userId)
                                        .year(year)
                                        .week(week)
                                        .volume(0)
                                        .updatedAt(LocalDateTime.now())
                                        .build();

    }

}
