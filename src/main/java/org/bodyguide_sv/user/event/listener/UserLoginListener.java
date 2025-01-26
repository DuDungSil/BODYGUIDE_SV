package org.bodyguide_sv.user.event.listener;

import java.util.UUID;

import org.bodyguide_sv.auth.event.UserLoginEvent;
import org.bodyguide_sv.auth.event.UserRefreshEvent;
import org.bodyguide_sv.user.service.UserMetaService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserLoginListener {
    
    private final UserMetaService userMetaService;

    @Async
    @EventListener
    public void handleLoginEvent(UserLoginEvent event) {

        UUID userId = event.getUserId();

        userMetaService.updateLastLoginAt(userId);
    }
    
    @Async
    @EventListener
    public void handleRefreshEvent(UserRefreshEvent event) {

        UUID userId = event.getUserId();

        userMetaService.updateLastLoginAt(userId);
    }
    

}
