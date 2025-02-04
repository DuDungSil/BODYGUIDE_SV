package org.bodyguide_sv.nutrition.event.listener;

import java.util.UUID;

import org.bodyguide_sv.nutrition.service.UserNutritionProfileService;
import org.bodyguide_sv.user.event.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NutritionInitializer {
    
    private final UserNutritionProfileService userNutritionProfileService;

    @EventListener
    public void handleUserRegisterEvent(UserRegisterEvent event) {
        UUID userId = event.getUserId();

        // UsersNutritionProfile 생성
        userNutritionProfileService.createUsersNutritionProfile(userId);

    }


}
