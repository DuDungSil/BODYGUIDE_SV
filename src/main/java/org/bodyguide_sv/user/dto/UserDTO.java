package org.bodyguide_sv.user.dto;

import java.util.UUID;

import org.bodyguide_sv.auth.enums.SocialProvider;
import org.bodyguide_sv.user.enums.Role;

public record UserDTO(
    UUID userId,
    Role role,
    SocialProvider provider,
    String providerId,
    String email,
    String name,
    Boolean isDelete
) {
    
}
