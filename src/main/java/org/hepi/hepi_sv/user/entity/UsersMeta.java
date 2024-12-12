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
@Table(name = "USERS_META")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersMeta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meta_id", updatable = false, nullable = false)
    private Long metaId;

    @Column(name = "user_id", updatable = false, nullable = false)
    private UUID userId;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "source")
    private String source;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
    
}
