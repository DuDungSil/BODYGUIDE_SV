package org.bodyguide_sv.exercise.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bodyguide_sv.exercise.controller.response.ExerciseReportResponse;
import org.bodyguide_sv.exercise.dto.ExerciseAbility;
import org.bodyguide_sv.exercise.dto.ExerciseAnalysisProfile;
import org.bodyguide_sv.exercise.dto.MuscleProfile;
import org.bodyguide_sv.exercise.dto.UserExerciseStatsDTO;
import org.bodyguide_sv.exercise.enums.MuscleGroupType;
import static org.bodyguide_sv.exercise.enums.MuscleGroupType.ARM;
import static org.bodyguide_sv.exercise.enums.MuscleGroupType.CORE;
import org.bodyguide_sv.user.dto.UserProfileDTO;
import org.bodyguide_sv.user.service.UserProfileService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExerciseReportService {
    
    private final UserProfileService userProfileService;
    private final UserExerciseStatsService userExerciseProfileService;
    private final ExerciseAnalysisService exerciseAnalysisService;
    private final ExerciseMetaService exerciseMetaService;

    public ExerciseReportResponse getReportResponse(UUID userId) {

        // 1. db 에서 프로필 가져오기

        // db 에서 유저 프로필 가져오기
        UserProfileDTO userProfile = userProfileService.getUserProfileDTO(userId);
        // db 에서 운동 프로필 가져오기
        UserExerciseStatsDTO userExerciseProfile = userExerciseProfileService.getUserExerciseProfileDTO(userId);

        // 2. 분석

        // 종목별 수행능력
        ExerciseAbility ability = new ExerciseAbility();
        ability.setBench(exerciseAnalysisService.analyzeExercise(120, userProfile.gender(), userProfile.weight(),
                userExerciseProfile.benchWeight(), userExerciseProfile.benchReps()));
        ability.setSquat(exerciseAnalysisService.analyzeExercise(251, userProfile.gender(), userProfile.weight(),
                userExerciseProfile.squatWeight(), userExerciseProfile.squatReps()));
        ability.setDead(exerciseAnalysisService.analyzeExercise(1, userProfile.gender(), userProfile.weight(),
                userExerciseProfile.deadWeight(), userExerciseProfile.deadReps()));
        ability.getDead().setMuscleGroupType(CORE); // 현재 전신
        ability.setOverhead(exerciseAnalysisService.analyzeExercise(150, userProfile.gender(), userProfile.weight(),
                userExerciseProfile.overheadWeight(), userExerciseProfile.overheadReps()));
        ability.setPushup(exerciseAnalysisService.analyzeExercise(132, userProfile.gender(), userProfile.weight(), 0,
                userExerciseProfile.pushupReps()));
        ability.getPushup().setMuscleGroupType(ARM); // 현재 가슴       
        ability.setPullup(exerciseAnalysisService.analyzeExercise(90, userProfile.gender(), userProfile.weight(), 0,
                userExerciseProfile.pullupReps()));

        // 운동 총 점수
        int totalScore = (int) ((ability.getBench().getScore() + ability.getSquat().getScore()
                + ability.getDead().getScore() + ability.getOverhead().getScore() + ability.getPushup().getScore()
                + ability.getPullup().getScore()) / 6);

        // 운동 수준
        String totalLevel = exerciseAnalysisService.getLevel(totalScore);

        // 상위 퍼센트
        double topPercent = 100 - (99.0 * totalScore / 120);

        // 3대 중량
        int bigThree = (int) (ability.getBench().getStrength() + ability.getSquat().getStrength()
                + ability.getDead().getStrength());

        // 상대적으로 약한 부위
        List<MuscleProfile> weekMuscle = getWeekBodyPartList(ability);

        return new ExerciseReportResponse(totalScore, totalLevel, topPercent, bigThree, ability, weekMuscle);
    }

    // 상대적으로 약한 부위
    private List<MuscleProfile> getWeekBodyPartList(ExerciseAbility ability) {
        // 1. 모든 운동 프로필 수집
        List<ExerciseAnalysisProfile> profiles = Arrays.asList(
            ability.getBench(),
            ability.getSquat(),
            ability.getDead(),
            ability.getOverhead(),
            ability.getPushup(),
            ability.getPullup()
        );
    
        // 2. 점수 기준 정렬
        profiles.sort(Comparator.comparingDouble(ExerciseAnalysisProfile::getScore));
    
        // 3. 상위 두 가지와 점수 40 미만 부위 식별 (중복 제거)
        Set<MuscleGroupType> targetMuscleGroups = new LinkedHashSet<>();
        for (int i = 0; i < Math.min(2, profiles.size()); i++) {
            targetMuscleGroups.add(profiles.get(i).getMuscleGroupType());
        }
        for (ExerciseAnalysisProfile profile : profiles) {
            if (profile.getScore() < 40) {
                targetMuscleGroups.add(profile.getMuscleGroupType());
            }
        }
    
        // 4. 식별된 근육 그룹에 대해 쿼리 수행 (기존 쿼리 활용)
        List<MuscleProfile> result = new ArrayList<>();
        for (MuscleGroupType muscleGroup : targetMuscleGroups) {
            // 4-1. Strength 가져오기
            String strength = exerciseMetaService.getStrengthByMuscleGroup(muscleGroup);
    
            // 4-2. 세부 근육 부위 가져오기
            List<String> details = exerciseMetaService.getDetailMuscleByMuscleGroup(muscleGroup);
    
            // 4-3. DTO 생성 및 결과에 추가
            MuscleProfile dto = new MuscleProfile();
            dto.setStrength(strength);
            dto.setDetails(details);
            result.add(dto);
        }
    
        return result;
    }

}
