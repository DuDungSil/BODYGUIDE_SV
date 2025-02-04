package org.bodyguide_sv.weight.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class WeightUpdatedEvent {
    
    private final UUID userId;

    public WeightUpdatedEvent(UUID userId) {
        this.userId = userId;
    }

}
