package org.bodyguide_sv.exercise.service;

import java.time.Duration;

import org.bodyguide_sv.exercise.dto.ExerciseAnalysisData;
import org.bodyguide_sv.exercise.dto.ExerciseData;
import org.bodyguide_sv.exercise.entity.Exercise;
import org.bodyguide_sv.exercise.repository.ExerciseRepository;
import org.bodyguide_sv.exercise.repository.custom.ExerciseQueryRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExerciseInfoService {
    
    private final ExerciseRepository exerciseRepository;
    private final ExerciseQueryRepository exerciseQueryRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    // 단일 운동 정보 데이터 가져오기 & 캐싱
    public ExerciseData getExerciseData(int exerciseId) {
        String cacheKey = "exerciseData:" + exerciseId;

        // Redis에서 데이터 조회
        ExerciseData cachedData = (ExerciseData) redisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return cachedData; // 캐시 데이터 반환
        }

        // DB에서 데이터 조회
        Exercise exercise = exerciseRepository.findById(exerciseId).orElseThrow(() -> new IllegalArgumentException("Invalid exercise ID: " + exerciseId));
        ExerciseData exerciseData = new ExerciseData(
                                            exercise.getExerId(),
                                            exercise.getExerName(),
                                            exercise.getExerNameKr(),
                                            exercise.getExerType(),
                                            exercise.getMuscleId(),
                                            exercise.getThresholdType());

        // Redis에 데이터 저장
        redisTemplate.opsForValue().set(cacheKey, exerciseData, Duration.ofHours(1));

        return exerciseData;
    }

    // 운동 분석 전용 데이터 가져오기 & 캐싱
    public ExerciseAnalysisData getExerciseAnalysisData(int exerciseId, String gender) {
        String cacheKey = "exerciseAnalysisData:" + exerciseId + ":" + gender;

        // Redis에서 데이터 조회
        ExerciseAnalysisData cachedData = (ExerciseAnalysisData) redisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return cachedData; // 캐시 데이터 반환
        }

        // DB에서 데이터 조회
        ExerciseAnalysisData exerciseAnalysisData = exerciseQueryRepository.findExerciseAnalysisData(exerciseId, gender);

        // Redis에 데이터 저장
        redisTemplate.opsForValue().set(cacheKey, exerciseAnalysisData, Duration.ofHours(1));

        return exerciseAnalysisData;
    }

    // 캐싱 파일 삭제
    public void invalidateCache(int exerciseId, String gender) {
        String cacheKey = "exerciseData:" + exerciseId + ":" + gender;
        redisTemplate.delete(cacheKey);
    }

}
