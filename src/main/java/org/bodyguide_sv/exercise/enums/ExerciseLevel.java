package org.bodyguide_sv.exercise.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ExerciseLevel {

    BEGINNER(1, "입문자", 0, 20),         // 입문자
    NOVICE(2, "초보자", 20, 40),          // 초보자
    INTERMEDIATE(3, "중급자", 40, 60),    // 중급자
    ADVANCED(4, "숙련자", 60, 80),        // 숙련자
    EXPERT(5, "고급자", 80, 100),         // 고급자
    ATHLETE(6, "운동선수", 100, 120);     // 운동선수

    private final int id;                 // 레벨 ID
    private final String name;            // 레벨 이름
    private final double minScoreThreshold;    // 최소 점수
    private final double maxScoreThreshold;    // 최대 점수

    public int getId() { return id; }
    public String getName() { return name; }
    public double getMinScoreThreshold() { return minScoreThreshold; }
    public double getMaxScoreThreshold() { return maxScoreThreshold; }
    
    // ID를 기반으로 ExerciseLevel 반환
    public static ExerciseLevel fromId(int id) {
        for (ExerciseLevel level : ExerciseLevel.values()) {
            if (level.id == id) {
                return level;
            }
        }
        throw new IllegalArgumentException("Invalid id: " + id);
    }
}