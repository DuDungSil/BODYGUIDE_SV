package org.bodyguide_sv.exercise.event;

import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public class ExerciseRecordChangedEvent {
    
    private final UUID userId;

    private final List<Integer> changedExerciseIdList;

    public ExerciseRecordChangedEvent(UUID userId, List<Integer> changedExerciseIdList) {
        this.userId = userId;
        this.changedExerciseIdList = changedExerciseIdList;
    }

}
