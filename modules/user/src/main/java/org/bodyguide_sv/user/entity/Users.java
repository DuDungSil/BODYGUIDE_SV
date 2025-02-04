package org.bodyguide_sv.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.common.enums.Role;
import org.bodyguide_sv.common.enums.SocialProvider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "USERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", columnDefinition = "BINARY(16)", updatable = false, nullable = false) 
    private UUID userId;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(name = "role")
    private Role role; 

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private SocialProvider provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "email")
    private String email;
    
    @Column(name = "name")
    private String name;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Boolean isDeleted() {
        return isDelete;
    }

    public void hardDeleteUser() {
        this.role = Role.GUEST;
        this.provider = null;
        this.providerId = null;
        this.email = null;
        this.name = null;
    }

    public void deleteUser() {
        this.isDelete = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void recoveryUser() {
        this.isDelete = false;
        this.deletedAt = null;
    }

    public void upgradeRole() {
        this.role = Role.USER;
    }

}
