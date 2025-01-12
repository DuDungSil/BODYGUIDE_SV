package org.bodyguide_sv.exercise.service;

import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.dto.MuscleGroupScoreDto;
import org.bodyguide_sv.exercise.dto.UpdatedMuscleScoreDTO;
import org.bodyguide_sv.exercise.entity.UsersMuscleScoreProfile;
import org.bodyguide_sv.exercise.repository.ExerciseQueryRepository;
import org.bodyguide_sv.exercise.repository.UsersMuscleScoreProfileRepository;
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

    // 생성
    public void createUserMuscleScoreProfile(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a exercise profile.");
        }
        
        UsersMuscleScoreProfile newProfile = UsersMuscleScoreProfile.createDefaultProfile(userId);

        usersMuscleScoreProfileRepository.save(newProfile);

    }

    @Transactional
    public UpdatedMuscleScoreDTO updateMuscleScoreProfile(UUID userId, List<Integer> exerciseIds) {

        // BestScore에서 가져온 score 데이터
        List<MuscleGroupScoreDto> muscleGroupScoreList = exerciseQueryRepository.getMaxScoreAndExerciseIdByMuscleGroup(userId);

        // BigThreeProfile 가져오기
        UsersMuscleScoreProfile profile = usersMuscleScoreProfileRepository.findByUserId(userId)
                .orElseGet(
                        () -> {
                            UsersMuscleScoreProfile newProfile = UsersMuscleScoreProfile.createDefaultProfile(userId);
                            return usersMuscleScoreProfileRepository.save(newProfile);
                        });

        for (MuscleGroupScoreDto muscleGroupScoreDto : muscleGroupScoreList) {
            log.debug(muscleGroupScoreDto.toString());
            switch (muscleGroupScoreDto.muscleGroupType()) {
                case CORE -> profile.updateCore(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.maxScore());
                case LOWER_BODY ->
                    profile.updateLowerBody(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.maxScore());
                case BACK ->
                    profile.updateBack(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.maxScore());
                case CHEST ->
                    profile.updateChest(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.maxScore());
                case SHOULDER ->
                    profile.updateShoulder(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.maxScore());
                case ARM -> profile.updateArm(muscleGroupScoreDto.exerciseId(), muscleGroupScoreDto.maxScore());
            }
        }

        UpdatedMuscleScoreDTO updatedMuscleScoreDTO = new UpdatedMuscleScoreDTO(
                                                                profile.getCore().getScore(), 
                                                                profile.getLowerBody().getScore(), 
                                                                profile.getBack().getScore(), 
                                                                profile.getChest().getScore(), 
                                                                profile.getShoulder().getScore(), 
                                                                profile.getArm().getScore() );

        usersMuscleScoreProfileRepository.save(profile);

        return updatedMuscleScoreDTO;
    }

}
