package org.bodyguide_sv.weight.event.listener;

import java.util.UUID;

import org.bodyguide_sv.weight.event.WeightUpdatedEvent;
import org.bodyguide_sv.weight.service.UserWeightProfileService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WeightProfileUpdateListener {
    
    private final UserWeightProfileService userWeightProfileService;

    @Async
    @EventListener
    public void updateWeightProfile(WeightUpdatedEvent event){
        UUID userId = event.getUserId();

        // 비동기 갱신 작업 처리
        System.out.println("체중 변경 이벤트 for userId: " + userId);

        // 유저 체중 프로필 갱신 
        userWeightProfileService.updateWeightProfile(userId);
    }

}
