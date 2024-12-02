package org.hepi.hepi_sv.exercise.service;

import java.util.List;

import org.hepi.hepi_sv.exercise.dto.ExerciseAnalysisProfile;
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

    public String getLevel(double score){
        double[] thresholds = {20, 40, 60, 80, 100, 120};
        String[] levels = {"입문자", "초보자", "중급자", "숙련자", "고급자", "운동선수"};

        String level = levels[levels.length - 1];
        for(int i = 0; i < thresholds.length; i++){
            if(score < thresholds[i]){
                level = levels[i];
                break;
            }
        }

        return level;
    }

    public ExerciseAnalysisProfile analyzeExercise(int exerciseId, String gender, double bodyWeight, double liftingWeight, int reps) {
        ExerciseAnalysisProfile profile = new ExerciseAnalysisProfile();
    
        String muscle = exerciseQueryRepository.findMuscleGroupIdNameByExerName(exerciseId);

        // 성별에 따른 기준 설정
        List<Double> thresholds = exerciseQueryRepository.findThresholds(exerciseId, gender);
        double[] scores = {0, 20, 40, 60, 80, 100, 120};
        
        double SP, strength;
        long threshold_type = exerciseQueryRepository.findThresholdTypeByExerName(exerciseId);
        if(threshold_type == 0) { // db
            SP = reps;
            strength = reps;
        }
        else{
            double RM1 = (reps == 1) ? liftingWeight : getRM1(liftingWeight, reps);
            SP = RM1 / bodyWeight;
            strength = RM1;
        }

        double score = 120;
    
        // SP에 따른 점수와 레벨 계산
        for (int i = 0; i < thresholds.size() - 1; i++) {
            if (SP >= thresholds.get(i) && SP < thresholds.get(i + 1)) {
                score = scores[i] + 20 * (SP - thresholds.get(i)) / (thresholds.get(i + 1) - thresholds.get(i));
                break;
            }
        }
    
        if (score >= 120) {
            score = 120;
        }

        String level = getLevel(score); 
    
        // 평균
        double average;
        if(threshold_type == 0){
            average = thresholds.get(2);
        }
        else {
            average = thresholds.get(2) * bodyWeight;
        }
        
        // 결과 저장
        profile.setExerId(exerciseId);
        profile.setMuscle(muscle);
        profile.setScore(score);
        profile.setLevel(level);
        profile.setStrength(Math.floor(strength));
        profile.setAverage((int)average); // 몰라서 임의설정
        
        return profile;
    }

}