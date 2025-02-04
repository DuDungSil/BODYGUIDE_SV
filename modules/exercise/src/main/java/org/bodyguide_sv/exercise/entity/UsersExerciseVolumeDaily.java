package org.bodyguide_sv.exercise.entity;

import java.time.LocalDate;
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
@Table(name = "USERS_EXERCISE_VOLUME_DAILY")
public class UsersExerciseVolumeDaily {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false) 
    private Long id;

    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "volume")
    private double volume;

    @Column(name = "exercise_date")
    private LocalDate date;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void updateVolume(double volume) {
        this.volume = volume;
        this.updatedAt = LocalDateTime.now();
    }

    public static UsersExerciseVolumeDaily create(UUID userId, LocalDate date){
        return UsersExerciseVolumeDaily.builder()
                                        .userId(userId)
                                        .date(date)
                                        .volume(0)
                                        .updatedAt(LocalDateTime.now())
                                        .build();

    }

}
