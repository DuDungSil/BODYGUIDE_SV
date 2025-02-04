package org.bodyguide_sv.exercise.event;

import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public class ExerciseBestScoreChangedEvent {
    
    private final UUID userId;

    private final List<Integer> updatedWeightAndScoreExerIds;

    private final List<Integer> updatedScoreIds;

    public ExerciseBestScoreChangedEvent(UUID userId, List<Integer> updatedWeightAndScoreExerIds, List<Integer> updatedScoreIds) {
        this.userId = userId;
        this.updatedWeightAndScoreExerIds = updatedWeightAndScoreExerIds;
        this.updatedScoreIds = updatedScoreIds;
    }

}
