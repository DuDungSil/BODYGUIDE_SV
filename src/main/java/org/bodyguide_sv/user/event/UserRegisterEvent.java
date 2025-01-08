package org.bodyguide_sv.user.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UserRegisterEvent {
    
    private final UUID userId;

    public UserRegisterEvent(UUID userId) {
        this.userId = userId;
    }

}
