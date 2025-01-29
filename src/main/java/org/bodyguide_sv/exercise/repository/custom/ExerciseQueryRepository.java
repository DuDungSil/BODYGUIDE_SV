package org.bodyguide_sv.exercise.repository.custom;

import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.dto.ExerciseAnalysisData;
import org.bodyguide_sv.exercise.dto.MuscleGroupScoreDto;
import org.springframework.stereotype.Repository;


@Repository
public interface ExerciseQueryRepository {
    
    // 운동 목적 가져오기
    List<String> findAllPurposes();

	// 운동 분석에 필요한 데이터 가져오기
	ExerciseAnalysisData findExerciseAnalysisData(int exerciseId, String gender);

	// 운동 이름으로 운동 점수 임계점 가져오기
	List<Double> findThresholds(int exerciseId, String gender);

	// 운동 임계점 유형 가져오기
	int findThresholdTypeByExerName(int exerciseId);

    // 운동 id로 운동 근육 그룹 이름 가져오기
	String findMuscleGroupIdNameByExerName(int exerciseId);

    // 운동 근육 그룹 id로 관련 strength 가져오기
	String findStrengthByMuscleGroupName(int muscleGroupId);

    // 운동 근육 그룹 id로 관련 세부 근육 부위 가져오기
	List<String> findMuscleDetailsByMuscleGroupName(int muscleGroupId);

    // 근육 그룹 그룹화해서 그룹의 최고점수, 무게, 횟수 가져오기
	List<MuscleGroupScoreDto> getMaxScoreAndExerciseIdByMuscleGroup(UUID userId);

}
