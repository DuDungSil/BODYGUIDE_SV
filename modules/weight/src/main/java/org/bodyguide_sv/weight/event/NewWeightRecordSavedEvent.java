package org.bodyguide_sv.weight.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class NewWeightRecordSavedEvent {
    
    private final UUID userId;

    public NewWeightRecordSavedEvent(UUID userId) {
        this.userId = userId;
    }

}
