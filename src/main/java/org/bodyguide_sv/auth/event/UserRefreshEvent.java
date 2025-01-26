package org.bodyguide_sv.auth.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class UserRefreshEvent {
    
    private final UUID userId;

    public UserRefreshEvent(UUID userId) {
        this.userId = userId;
    }

}
