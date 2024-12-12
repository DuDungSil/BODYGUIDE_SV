package org.hepi.hepi_sv.user.entity;

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
@Table(name = "USERS_PROFILE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", updatable = false, nullable = false)
    private Long profileId;

    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "gender")
    private String gender;

    @Column(name = "height")
    private double height;
    
    @Column(name = "weight")
    private double weight;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "profile_img")
    private String profileImg;

    @Column(name = "intro_text")
    private String introText;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
