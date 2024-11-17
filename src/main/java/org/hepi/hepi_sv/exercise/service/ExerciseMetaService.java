package org.hepi.hepi_sv.exercise.service;

import java.util.List;

import org.hepi.hepi_sv.exercise.repository.ExerciseQueryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExerciseMetaService {

    private final ExerciseQueryRepository exerciseQueryRepository;

    // 운동 목적 가져오기
    public List<String> getExercisePurpose() {
        List<String> list = exerciseQueryRepository.findAllPurposes();

        return list;
    }

    // 근육 그룹 관련 힘 가져오기
    public String getStrengthByMuscleGroup(String muscleGroup) {
        String strength = exerciseQueryRepository.findStrengthByMuscleGroupName(muscleGroup);
                
        return strength;
    }

    // 근육 그룹 관련 세부 근육 가져오기
    public List<String> getDetailMuscleByMuscleGroup(String muscleGroup) {
        List<String> details = exerciseQueryRepository.findMuscleDetailsByMuscleGroupName(muscleGroup);

        return details;
    }

}
