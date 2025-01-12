package org.bodyguide_sv.exercise.event.listener;

import java.util.UUID;

import org.bodyguide_sv.exercise.service.UserBigThreeProfileService;
import org.bodyguide_sv.exercise.service.UserExerciseMuscleScoreProfileService;
import org.bodyguide_sv.exercise.service.UserExerciseStatsService;
import org.bodyguide_sv.exercise.service.UserExerciseTotalPerformanceService;
import org.bodyguide_sv.user.event.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExerciseInitializer {
    
    private final UserExerciseStatsService userExerciseProfileService;
    private final UserBigThreeProfileService userBigThreeProfileService;
    private final UserExerciseMuscleScoreProfileService userExerciseMuscleScoreProfileService;
    private final UserExerciseTotalPerformanceService userExerciseTotalPerformanceService;

    @Transactional
    @EventListener
    public void handleUserRegisterEvent(UserRegisterEvent event) {
        UUID userId = event.getUserId();

        // UsersExerciseStats 생성
        userExerciseProfileService.createUsersExerciseProfile(userId);

        // UsersBigThreeProfile 생성
        userBigThreeProfileService.createUserBigThreeProfile(userId);

        // UsersMuscleScoreProfile 생성
        userExerciseMuscleScoreProfileService.createUserMuscleScoreProfile(userId);

        // UsersExerciseTotalPerformance 생성
        userExerciseTotalPerformanceService.createUserExerciseTotalPerformance(userId);

    }

}
