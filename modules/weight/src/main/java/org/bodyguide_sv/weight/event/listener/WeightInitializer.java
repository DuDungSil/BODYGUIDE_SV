package org.bodyguide_sv.weight.event.listener;

import java.util.UUID;

import org.bodyguide_sv.user.event.UserRegisterEvent;
import org.bodyguide_sv.weight.service.UserWeightProfileService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeightInitializer {
    
    private final UserWeightProfileService userWeightProfileService;

    @Transactional
    @EventListener
    public void handleUserRegisterEvent(UserRegisterEvent event) {
        UUID userId = event.getUserId();

        // UsersWeightProfile 생성
        userWeightProfileService.createUsersWeightProfile(userId);

    }

}
