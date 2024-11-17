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
@Table(name = "USERS_SOCIAL_TOKEN")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersProviderToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id", updatable = false, nullable = false)
    private Long tokenId;

    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "refresh_token")
    private String refreshToken;

}
