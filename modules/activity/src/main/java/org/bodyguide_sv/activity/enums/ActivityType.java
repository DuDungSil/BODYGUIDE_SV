package org.bodyguide_sv.activity.enums;

public enum ActivityType {
    EXERCISE(101, 5, 4), // 운동 기록
    FOOD(102, 5, 4), // 음식 기록
    WEIGHT(103, 5, 2); // 체중 기록

    private final int code;   // 활동 코드    
    private final int exp;    // 1회 활동시 획득 경험치
    private final int maxDailyCount;      // 하루 최대 경험치 획득 횟수

    ActivityType(int code, int exp, int maxDailyCount) {
        this.code = code;
        this.exp = exp;
        this.maxDailyCount = maxDailyCount;
    }

    public int getCode() {
        return code;
    }

    public int getExp() {
        return exp;
    }

    public int getMaxDailyCount() {
        return maxDailyCount;
    }

    // 코드값으로 ActivityType 찾기
    public static ActivityType fromCode(int code) {
        for (ActivityType type : values()) {
            if (type.code == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid code for ActivityType: " + code);
    }
    
}