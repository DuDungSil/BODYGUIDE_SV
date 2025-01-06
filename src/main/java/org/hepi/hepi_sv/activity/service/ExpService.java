package org.hepi.hepi_sv.activity.service;

import java.util.UUID;

import org.hepi.hepi_sv.activity.entity.LevelInfo;
import org.hepi.hepi_sv.activity.entity.UsersExpProfile;
import org.hepi.hepi_sv.activity.enums.ActivityType;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExpService {
    
    private final LevelInfoService levelInfoService;
    private final UserExpProfileService userExpProfileService;

    // 경험치 획득 ( 레벨업 체크 ) 
    @Transactional
    public void processExperienceGain(UUID userId, ActivityType activityType) {

        // 경험치 획득
        addExperience(userId, activityType);

    }

    // 경험치 획득
    private void addExperience(UUID userId, ActivityType activityType) {

        // 사용자 레벨 정보 가져오기
        UsersExpProfile expProfile = userExpProfileService.getUserExpProfile(userId);
        int totalExp = expProfile.getTotalExp();

        // 경험치 추가
        int increasedTotalExp = totalExp + activityType.getExp();

        // 레벨 계산
        int newCurrentLevel = calculateCurrentLevel(increasedTotalExp);

        // 상태 업데이트
        expProfile.updateExperience(increasedTotalExp, newCurrentLevel);
        userExpProfileService.updateUserExpProfile(expProfile);
    }
    
    // 레벨 계산
    private int calculateCurrentLevel(int totalExp) {
        int maxLevel = levelInfoService.getMaxLevel();
        for (int level = 1; level <= maxLevel; level++) {
            LevelInfo levelInfo = levelInfoService.getLevelInfo(level);
            if (totalExp < levelInfo.getTotalExp()) {
                return level; 
            }
        }
        return maxLevel; // 최대 레벨 도달
    }

}
