package org.bodyguide_sv.auth.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UserLoginEvent {
    
    private final UUID userId;

    public UserLoginEvent(UUID userId) {
        this.userId = userId;
    }

}
