package org.bodyguide_sv.exercise.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class NewExerciseRecordSavedEvent {
    
    private final UUID userId;

    public NewExerciseRecordSavedEvent(UUID userId) {
        this.userId = userId;
    }

}
