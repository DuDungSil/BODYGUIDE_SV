package org.bodyguide_sv.exercise.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MuscleGroupType {
    
    CORE(1, "코어"),
    LOWER_BODY(2, "하체"),
    BACK(3, "등"),
    CHEST(4, "가슴"),
    SHOULDER(5, "어깨"),
    ARM(6, "팔");
               
    private final Integer muscleGroupId;
    private final String description;

    public int getMuscleGroupId() {
        return this.muscleGroupId;
    }

    public static MuscleGroupType fromId(int id) {
        for (MuscleGroupType type : MuscleGroupType.values()) {
            if (type.muscleGroupId == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid muscleGroupId: " + id);
    }
}
