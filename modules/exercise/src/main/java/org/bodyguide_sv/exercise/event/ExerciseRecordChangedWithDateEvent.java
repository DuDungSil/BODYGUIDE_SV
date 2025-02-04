package org.bodyguide_sv.exercise.event;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;

@Getter
public class ExerciseRecordChangedWithDateEvent {
    
    private final UUID userId;

    private final LocalDateTime date;

    public ExerciseRecordChangedWithDateEvent(UUID userId, LocalDateTime date) {
        this.userId = userId;
        this.date = date;
    }

}
