package org.hepi.hepi_sv.activity.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.hepi.hepi_sv.activity.entity.LevelInfo;
import org.hepi.hepi_sv.activity.entity.UsersActivityLog;
import org.hepi.hepi_sv.activity.entity.UsersExpProfile;
import org.hepi.hepi_sv.activity.enums.ActivityType;
import org.hepi.hepi_sv.activity.repository.UsersActivityLogRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExpService {

    private final LevelInfoService levelInfoService;
    private final UserExpProfileService userExpProfileService;
    private final UsersActivityLogRepository usersActivityLogRepository;

    // 경험치 획득 ( 레벨업 체크 , 활동 기록 ) 
    @Transactional
    public void processExperienceGain(UUID userId, ActivityType activityType) {
        // 활동 기록 남기기
        int dailyCount = recordActivityLog(userId, activityType);

        // 일일 획득 횟수를 초과했는지 확인
        if (dailyCount <= activityType.getMaxDailyCount()) {
            // 경험치 획득
            addExperience(userId, activityType);
        }
    }

    // 활동 횟수 기록 로그 
    private int recordActivityLog(UUID userId, ActivityType activityType) {
        LocalDate today = LocalDate.now();

        // 기존 활동 로그 조회
        Optional<UsersActivityLog> existingLogOpt = usersActivityLogRepository.findAll().stream()
                .filter(log -> log.getUserId().equals(userId) && log.getDate().equals(today)
                        && log.getActivityCode() == activityType.getCode())
                .findFirst();

        UsersActivityLog activityLog;
        if (existingLogOpt.isPresent()) {
            // 로그가 있으면 dailyCount 증가
            UsersActivityLog existingLog = existingLogOpt.get();
            activityLog = UsersActivityLog.builder()
                    .id(existingLog.getId())
                    .userId(userId)
                    .date(today)
                    .activityCode(activityType.getCode())
                    .dailyCount(existingLog.getDailyCount() + 1)
                    .activityType(activityType)
                    .build();
        } else {
            // 로그가 없으면 새로 생성
            activityLog = UsersActivityLog.builder()
                    .userId(userId)
                    .date(today)
                    .activityCode(activityType.getCode())
                    .dailyCount(1)
                    .activityType(activityType)
                    .build();
        }

        // 저장 및 반환
        usersActivityLogRepository.save(activityLog);
        return activityLog.getDailyCount();
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
