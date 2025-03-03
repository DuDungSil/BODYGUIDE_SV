package org.bodyguide_sv.exercise.service;

import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.dto.MuscleGroupScoreDto;
import org.bodyguide_sv.exercise.dto.UpdatedMuscleScoreDTO;
import org.bodyguide_sv.exercise.dto.UserMuscleScoreProfileDTO;
import org.bodyguide_sv.exercise.dto.UserMuscleScoreProfileDTO.MuscleScore;
import org.bodyguide_sv.exercise.entity.UsersMuscleScoreProfile;
import org.bodyguide_sv.exercise.repository.UsersMuscleScoreProfileRepository;
import org.bodyguide_sv.exercise.repository.custom.ExerciseQueryRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserExerciseMuscleScoreProfileService {

    private final ExerciseQueryRepository exerciseQueryRepository;
    private final UsersMuscleScoreProfileRepository usersMuscleScoreProfileRepository;

    // 가져오기
    public UserMuscleScoreProfileDTO getUserMuscleScoreProfileDTO(UUID userId) {

        // UsersMuscleScoreProfile 가져오기
        UsersMuscleScoreProfile profile = usersMuscleScoreProfileRepository.findByUserId(userId)
                .orElseGet(
                        () -> {
                            UsersMuscleScoreProfile newProfile = UsersMuscleScoreProfile.createDefaultProfile(userId);
                            return usersMuscleScoreProfileRepository.save(newProfile);
                        });

        MuscleScore core = new MuscleScore(
                profile.getCore().getExerciseId(),
                profile.getCore().getWeight(),
                profile.getCore().getReps(),
                profile.getCore().getScore()
        );

        MuscleScore lowerBody = new MuscleScore(
                profile.getLowerBody().getExerciseId(),
                profile.getLowerBody().getWeight(),
                profile.getLowerBody().getReps(),
                profile.getLowerBody().getScore()
        );

        MuscleScore back = new MuscleScore(
                profile.getBack().getExerciseId(),
                profile.getBack().getWeight(),
                profile.getBack().getReps(),
                profile.getBack().getScore()
        );

        MuscleScore chest = new MuscleScore(
                profile.getChest().getExerciseId(),
                profile.getChest().getWeight(),
                profile.getChest().getReps(),
                profile.getChest().getScore()
        );

        MuscleScore shoulder = new MuscleScore(
                profile.getShoulder().getExerciseId(),
                profile.getShoulder().getWeight(),
                profile.getShoulder().getReps(),
                profile.getShoulder().getScore()
        );

        MuscleScore arm = new MuscleScore(
                profile.getArm().getExerciseId(),
                profile.getArm().getWeight(),
                profile.getArm().getReps(),
                profile.getArm().getScore()
        );

        return new UserMuscleScoreProfileDTO(core, lowerBody, back, chest, shoulder, arm);

    }

    // 생성
    public void createUserMuscleScoreProfile(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a exercise profile.");
        }

        UsersMuscleScoreProfile newProfile = UsersMuscleScoreProfile.createDefaultProfile(userId);

        usersMuscleScoreProfileRepository.save(newProfile);

    }

    // 업데이트
    @Transactional
    public UpdatedMuscleScoreDTO updateMuscleScoreProfile(UUID userId, List<Integer> exerciseIds) {

        // BestScore에서 가져온 score 데이터
        List<MuscleGroupScoreDto> muscleGroupScoreList = exerciseQueryRepository.getMaxScoreAndExerciseIdByMuscleGroup(userId);

        // UsersMuscleScoreProfile 가져오기
        UsersMuscleScoreProfile profile = usersMuscleScoreProfileRepository.findByUserId(userId)
                .orElseGet(
                        () -> {
                            UsersMuscleScoreProfile newProfile = UsersMuscleScoreProfile.createDefaultProfile(userId);
                            return usersMuscleScoreProfileRepository.save(newProfile);
                        });

        for (MuscleGroupScoreDto muscleGroupScoreDto : muscleGroupScoreList) {
            log.debug(muscleGroupScoreDto.toString());
            switch (muscleGroupScoreDto.muscleGroupType()) {
                case CORE ->
                    profile.updateCore(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.weight(), muscleGroupScoreDto.reps(), muscleGroupScoreDto.maxScore());
                case LOWER_BODY ->
                    profile.updateLowerBody(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.weight(), muscleGroupScoreDto.reps(), muscleGroupScoreDto.maxScore());
                case BACK ->
                    profile.updateBack(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.weight(), muscleGroupScoreDto.reps(), muscleGroupScoreDto.maxScore());
                case CHEST ->
                    profile.updateChest(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.weight(), muscleGroupScoreDto.reps(), muscleGroupScoreDto.maxScore());
                case SHOULDER ->
                    profile.updateShoulder(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.weight(), muscleGroupScoreDto.reps(), muscleGroupScoreDto.maxScore());
                case ARM ->
                    profile.updateArm(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.weight(), muscleGroupScoreDto.reps(), muscleGroupScoreDto.maxScore());
            }
        }

        UpdatedMuscleScoreDTO updatedMuscleScoreDTO = new UpdatedMuscleScoreDTO(
                profile.getCore().getScore(),
                profile.getLowerBody().getScore(),
                profile.getBack().getScore(),
                profile.getChest().getScore(),
                profile.getShoulder().getScore(),
                profile.getArm().getScore());

        usersMuscleScoreProfileRepository.save(profile);

        return updatedMuscleScoreDTO;
    }

}
