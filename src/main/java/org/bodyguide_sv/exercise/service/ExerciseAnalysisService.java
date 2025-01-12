package org.bodyguide_sv.exercise.service;

import java.util.List;

import org.bodyguide_sv.exercise.dto.ExerciseAnalysisData;
import org.bodyguide_sv.exercise.dto.ExerciseAnalysisProfile;
import org.bodyguide_sv.exercise.enums.ExerciseLevel;
import org.bodyguide_sv.exercise.enums.MuscleGroupType;
import org.bodyguide_sv.exercise.enums.ThresholdType;
import org.bodyguide_sv.exercise.repository.ExerciseQueryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExerciseAnalysisService {

    private final ExerciseQueryRepository exerciseQueryRepository;

    private double getRM1(double liftingWeight, int Reps){
        double W1 = liftingWeight * 0.025 * Reps;
        double RM1 = liftingWeight + W1;
        return RM1;
    }

    public ExerciseLevel getLevel(double score) {
        for (ExerciseLevel level : ExerciseLevel.values()) {
            if (score < level.getMaxScoreThreshold()) {
                return level; // 해당 점수 범위의 레벨 반환
            }
        }
        // 모든 레벨의 최대값을 초과하면 최고 레벨 반환
        return ExerciseLevel.ATHLETE;
    }

    private double calculateScore(double SP, List<Double> thresholds) {
        double score = ExerciseLevel.ATHLETE.getMaxScoreThreshold(); // 기본 최대 점수 (120)
    
        for (int i = 0; i < thresholds.size() - 1; i++) {
            if (SP >= thresholds.get(i) && SP < thresholds.get(i + 1)) {
                // Enum에서 현재 레벨과 다음 레벨의 점수 범위를 가져옴
                double minScore = ExerciseLevel.values()[i].getMinScoreThreshold();
                double maxScore = ExerciseLevel.values()[i].getMaxScoreThreshold();
    
                // 점수를 비율로 계산
                score = minScore + ((SP - thresholds.get(i)) / (thresholds.get(i + 1) - thresholds.get(i))) * (maxScore - minScore);
                break;
            }
        }
    
        // 소수점 두 자리까지 반올림
        return Math.round(score * 100.0) / 100.0;
    }

    public ExerciseAnalysisProfile analyzeExercise(int exerciseId, String gender, double bodyWeight, double liftingWeight, int reps) {
    
        // redis 처리 필요
        ExerciseAnalysisData exerciseData = exerciseQueryRepository.findExerciseData(exerciseId, gender);
        List<Double> thresholds = exerciseData.thresholds();
        MuscleGroupType muscleGroupType = exerciseData.muscleGroupType();
        ThresholdType thresholdType = exerciseData.thresholdType();
        
        double SP, strength;
        if(thresholdType.getTypeId() == 0) { // db
            SP = reps;
            strength = reps;
        }
        else{
            double RM1 = (reps == 1) ? liftingWeight : getRM1(liftingWeight, reps);
            SP = RM1 / bodyWeight;
            strength = RM1;
        }

        double score = calculateScore(SP, thresholds);
        ExerciseLevel level = getLevel(score);
    
        // 임계값으로 평균 계산 ( 나중에 삭제 )
        double average = (thresholdType.getTypeId() == 0) ? thresholds.get(2) : thresholds.get(2) * bodyWeight;
        
        // 결과 저장
        ExerciseAnalysisProfile profile = new ExerciseAnalysisProfile();
        profile.setExerId(exerciseId);
        profile.setMuscleGroupType(muscleGroupType);
        profile.setScore(score);
        profile.setLevel(level);
        profile.setStrength(Math.floor(strength));
        profile.setAverage((int)average); 
        
        return profile;
    }

}