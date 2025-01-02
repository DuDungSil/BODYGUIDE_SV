package org.hepi.hepi_sv.nutrition.enums;

import lombok.Getter;

@Getter
public enum PAType {
    INACTIVE(1, "비활동적", 1.2),
    LOW_ACTIVE(2, "저활동적", 1.375),
    ACTIVE(3, "활동적", 1.55),
    HIGH_ACTIVE(4, "고활동적", 1.725),
    VERY_ACTIVE(5, "매우활동적", 1.9);

    private final int id;          // ID
    private final String description; // 설명
    private final double value;    // VALUE

    PAType(int id, String description, double value) {
        this.id = id;
        this.description = description;
        this.value = value;
    }

    // 정적 메서드로 ID나 이름으로 Enum 검색 가능
    public static PAType fromId(int id) {
        for (PAType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid ID: " + id);
    }

    public static PAType fromName(String name) {
        for (PAType type : values()) {
            if (type.name().equalsIgnoreCase(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid name: " + name);
    }

    public static PAType fromDescription(String description) {
        for (PAType type : values()) {
            if (type.getDescription().equals(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid description: " + description);
    }

}
