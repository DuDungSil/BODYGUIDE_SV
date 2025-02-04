package org.bodyguide_sv.exercise.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ThresholdType {
    
    BODYWEIGHT_EXERCISE(0), // 맨몸운동
    WEIGHT_TRAINING(1); // 중량운동

    private final int typeId;

    public int getTypeId() {
        return this.typeId;
    }

    public static ThresholdType fromId(int id) {
        for (ThresholdType type : ThresholdType.values()) {
            if (type.typeId == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid typeId: " + id);
    }

}
