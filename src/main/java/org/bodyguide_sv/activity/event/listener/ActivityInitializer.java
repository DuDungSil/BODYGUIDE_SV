package org.bodyguide_sv.activity.event.listener;

import java.util.UUID;

import org.bodyguide_sv.activity.service.ActivityService;
import org.bodyguide_sv.activity.service.UserExpProfileService;
import org.bodyguide_sv.user.event.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ActivityInitializer {
    
    private final ActivityService activityService;
    private final UserExpProfileService userExpProfileService;

    @EventListener
    public void handleUserRegisterEvent(UserRegisterEvent event) {
        UUID userId = event.getUserId();

        // 액티비티 프로필 생성
        activityService.createUserActivityProfile(userId);

        // EXP 프로필 생성
        userExpProfileService.createUserExpProfile(userId);

    }

}
