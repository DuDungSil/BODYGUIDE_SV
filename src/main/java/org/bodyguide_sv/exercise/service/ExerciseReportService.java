package org.bodyguide_sv.exercise.service;

import java.util.*;
import java.util.stream.Collectors;

import org.bodyguide_sv.exercise.controller.response.ExerciseReportResponse;
import org.bodyguide_sv.exercise.dto.*;
import org.bodyguide_sv.exercise.dto.UserMuscleScoreProfileDTO.MuscleScore;
import org.bodyguide_sv.exercise.enums.MuscleGroupType;
import org.bodyguide_sv.user.dto.UserProfileDTO;
import org.bodyguide_sv.user.service.UserProfileService;
import org.bodyguide_sv.weight.dto.UserWeightProfileDTO;
import org.bodyguide_sv.weight.service.UserWeightProfileService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExerciseReportService {

    private final UserProfileService userProfileService;
    private final ExerciseAnalysisService exerciseAnalysisService;
    private final ExerciseMetaService exerciseMetaService;
    private final UserWeightProfileService userWeightProfileService;
    private final UserExerciseMuscleScoreProfileService userExerciseMuscleScoreProfileService;

    public ExerciseReportResponse getReportResponse(UUID userId) {
        // 1. 프로필 가져오기
        UserProfileDTO userProfile = userProfileService.getUserProfileDTO(userId);
        UserWeightProfileDTO weightProfile = userWeightProfileService.getWeightProfile(userId);
        UserMuscleScoreProfileDTO muscleScoreProfile = userExerciseMuscleScoreProfileService.getUserMuscleScoreProfileDTO(userId);

        String gender = userProfile.gender();
        double userWeight = Optional.ofNullable(weightProfile.weight()).orElse(userProfile.weight());

        // 2. 분석 수행
        ExerciseMuscleAbility ability = analyzeMuscleAbility(muscleScoreProfile, gender, userWeight);

        // 3. 총 점수 계산
        int totalScore = calculateTotalScore(ability);

        // 4. 운동 수준과 상위 퍼센트 계산
        String totalLevel = exerciseAnalysisService.getLevel(totalScore).getName();
        double topPercent = 100 - (99.0 * totalScore / 120);

        // 5. 약한 부위 식별
        List<MuscleProfile> weakMuscles = identifyWeakMuscles(ability);

        return new ExerciseReportResponse(totalScore, totalLevel, topPercent, ability, weakMuscles);
    }

    private ExerciseMuscleAbility analyzeMuscleAbility(UserMuscleScoreProfileDTO profile, String gender, double userWeight) {
        return new ExerciseMuscleAbility(
            analyzeProfile(profile.core(), gender, userWeight),
            analyzeProfile(profile.lowerBody(), gender, userWeight),
            analyzeProfile(profile.back(), gender, userWeight),
            analyzeProfile(profile.chest(), gender, userWeight),
            analyzeProfile(profile.shoulder(), gender, userWeight),
            analyzeProfile(profile.arm(), gender, userWeight)
        );
    }

    private ExerciseAnalysisProfile analyzeProfile(MuscleScore muscleScore, String gender, double userWeight) {
        if (muscleScore == null || muscleScore.exerciseId() == null) {
            return null;
        }
        return exerciseAnalysisService.analyzeExercise(
            muscleScore.exerciseId(), gender, userWeight, muscleScore.weight(), muscleScore.reps()
        );
    }

    private int calculateTotalScore(ExerciseMuscleAbility ability) {
        return (int) Arrays.asList(
            ability.core(), ability.lowerBody(), ability.back(),
            ability.chest(), ability.shoulder(), ability.arm()
        ).stream()
            .mapToDouble(profile -> Optional.ofNullable(profile).map(ExerciseAnalysisProfile::getScore).orElse(0.0))
            .average()
            .orElse(0.0);
    }

    private List<MuscleProfile> identifyWeakMuscles(ExerciseMuscleAbility ability) {
        // 1. 모든 프로필 수집 및 점수 정렬
        List<ExerciseAnalysisProfile> sortedProfiles = Arrays.asList(
            ability.core(), ability.lowerBody(), ability.back(),
            ability.chest(), ability.shoulder(), ability.arm()
        ).stream()
            .filter(Objects::nonNull)
            .sorted(Comparator.comparingDouble(ExerciseAnalysisProfile::getScore))
            .collect(Collectors.toList());

        // 2. 약한 부위 식별
        Set<MuscleGroupType> weakGroups = new LinkedHashSet<>();
        sortedProfiles.stream().limit(2).forEach(p -> weakGroups.add(p.getMuscleGroupType()));
        sortedProfiles.stream().filter(p -> p.getScore() < 40).forEach(p -> weakGroups.add(p.getMuscleGroupType()));

        // 3. 근육 정보 생성
        return weakGroups.stream()
            .map(this::createMuscleProfile)
            .collect(Collectors.toList());
    }

    private MuscleProfile createMuscleProfile(MuscleGroupType muscleGroup) {
        String strength = exerciseMetaService.getStrengthByMuscleGroup(muscleGroup);
        List<String> details = exerciseMetaService.getDetailMuscleByMuscleGroup(muscleGroup);

        MuscleProfile profile = new MuscleProfile();
        profile.setStrength(strength);
        profile.setDetails(details);
        return profile;
    }
}
