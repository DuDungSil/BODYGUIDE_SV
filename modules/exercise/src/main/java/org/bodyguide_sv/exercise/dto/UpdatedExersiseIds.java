package org.bodyguide_sv.exercise.dto;

import java.util.List;

public record UpdatedExersiseIds(
        List<Integer> updatedWeightAndScoreExerIds,
        List<Integer> updatedScoreIds) {

}
