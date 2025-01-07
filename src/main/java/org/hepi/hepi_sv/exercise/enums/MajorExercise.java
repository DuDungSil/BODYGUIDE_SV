package org.hepi.hepi_sv.exercise.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MajorExercise {
    
    BENCH_PRESS(120, "벤치 프레스"),
    SQUAT(251, "스쿼트"),
    DEAD_LIFT(1, "데드 리프트"),
    OVERHEAD_PRESS(150, "오버헤드 프레스"),
    PUSH_UP(132, "푸쉬 업"),
    PULL_UP(90, "풀 업");

    private final int exerId;
    private final String exerName;

    public int getExerId() {
        return this.exerId;
    }

}
