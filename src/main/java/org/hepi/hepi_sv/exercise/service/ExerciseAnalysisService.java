package org.hepi.hepi_sv.exercise.service;

import java.util.List;

import org.hepi.hepi_sv.exercise.dto.ExerciseAnalysisData;
import org.hepi.hepi_sv.exercise.dto.ExerciseAnalysisProfile;
import org.hepi.hepi_sv.exercise.enums.MuscleGroupType;
import org.hepi.hepi_sv.exercise.enums.ThresholdType;
import org.hepi.hepi_sv.exercise.repository.ExerciseQueryRepository;
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

    public String getLevel(double score) {
        double[] thresholds = { 20, 40, 60, 80, 100, 120 };
        String[] levels = { "입문자", "초보자", "중급자", "숙련자", "고급자", "운동선수" };

        String level = levels[levels.length - 1];
        for (int i = 0; i < thresholds.length; i++) {
            if (score < thresholds[i]) {
                level = levels[i];
                break;
            }
        }

        return level;
    }

    private double calculateScore(double SP, List<Double> thresholds) {
        double[] scores = {0, 20, 40, 60, 80, 100, 120};
        double score = 120;
    
        for (int i = 0; i < thresholds.size() - 1; i++) {
            if (SP >= thresholds.get(i) && SP < thresholds.get(i + 1)) {
                score = scores[i] + 20 * (SP - thresholds.get(i)) / (thresholds.get(i + 1) - thresholds.get(i));
                break;
            }
        }
    
        return Math.min(score, 120);
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
        String level = getLevel(score);
    
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