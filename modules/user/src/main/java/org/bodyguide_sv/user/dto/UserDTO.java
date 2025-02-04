package org.bodyguide_sv.user.dto;

import java.util.UUID;

import org.bodyguide_sv.common.enums.Role;
import org.bodyguide_sv.common.enums.SocialProvider;

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
