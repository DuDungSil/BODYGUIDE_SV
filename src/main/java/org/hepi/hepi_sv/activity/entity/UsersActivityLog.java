package org.hepi.hepi_sv.activity.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.hepi.hepi_sv.activity.enums.ActivityType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS_ACTIVITY_LOG")
public class UsersActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "activity_code", nullable = false)
    private int activityCode;

    @Column(name = "daily_count", nullable = false)
    private int dailyCount;

    @Transient
    private ActivityType activityType;

    @PostLoad
    public void loadActivityType() {
        this.activityType = ActivityType.fromCode(this.activityCode);
    }

    @PrePersist
    public void saveActivityType() {
        if (this.activityType != null) {
            this.activityCode = this.activityType.getCode();
        }
    }

}
