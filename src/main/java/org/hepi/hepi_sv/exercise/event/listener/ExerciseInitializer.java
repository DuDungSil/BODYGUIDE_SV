package org.hepi.hepi_sv.exercise.event.listener;

import java.util.UUID;

import org.hepi.hepi_sv.exercise.service.UserExerciseStatsService;
import org.hepi.hepi_sv.user.event.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ExerciseInitializer {
    
    private final UserExerciseStatsService userExerciseProfileService;

    @EventListener
    public void handleUserRegisterEvent(UserRegisterEvent event) {
        UUID userId = event.getUserId();

        // UserExerciseStats 생성
        userExerciseProfileService.createUsersExerciseProfile(userId);

    }

}
