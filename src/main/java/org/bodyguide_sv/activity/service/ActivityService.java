package org.bodyguide_sv.activity.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.bodyguide_sv.activity.dto.ActivityProfileResponse;
import org.bodyguide_sv.activity.entity.UsersActivityLog;
import org.bodyguide_sv.activity.entity.UsersActivityProfile;
import org.bodyguide_sv.activity.enums.ActivityType;
import org.bodyguide_sv.activity.event.ExpGainedEvent;
import org.bodyguide_sv.activity.repository.UsersActivityLogRepository;
import org.bodyguide_sv.activity.repository.UsersActivityProfileRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ActivityService {

    private final ApplicationEventPublisher eventPublisher;
    private final UsersActivityLogRepository usersActivityLogRepository;
    private final UsersActivityProfileRepository usersActivityProfileRepository;

    // 새로운 유저 ActivityProfile 생성
    public UsersActivityProfile createUserActivityProfile(UUID userId) {
        UsersActivityProfile newProfile = UsersActivityProfile.create(userId);
        return usersActivityProfileRepository.save(newProfile);
    }

    // 활동 기록 ( 활동 기록 ) 
    @Transactional
    public void processActivityCompleted(UUID userId, ActivityType activityType) {

        // 데일리 활동 기록 남기기
        int dailyCount = recordActivityLog(userId, activityType);

        // 유저 ActivityPofile 갱신
        updateUserActivityProfile(userId, activityType);

        // 일일 획득 횟수를 초과했는지 확인
        if (dailyCount <= activityType.getMaxDailyCount()) {
            // 경험치 획득 이벤트 발생
            ExpGainedEvent event = new ExpGainedEvent(userId, activityType);
            eventPublisher.publishEvent(event);
        }
    }

    // 데일리 활동 횟수 기록 로그 
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

    // 유저 ActivityProfile 갱신
    private void updateUserActivityProfile(UUID userId, ActivityType activityType) {

        // 유저의 ActivityProfile 가져오기
        UsersActivityProfile profile = usersActivityProfileRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new IllegalArgumentException("User Activity Profile not found for userId: " + userId));

        // ActivityType에 따라 프로필 업데이트
        switch (activityType) {
            case EXERCISE -> profile.updateExerciseCount();
            case FOOD -> profile.updateDietCount();
            case WEIGHT -> profile.updateWeightCount();
            default -> throw new IllegalArgumentException("Unsupported ActivityType: " + activityType);
        }

        // 업데이트된 프로필 저장
        usersActivityProfileRepository.save(profile);
    }

    // 유저 활동 프로필 응답 생성
    public ActivityProfileResponse getActivityProfileResponse(UUID userId) {
        // 유저의 활동 프로필을 가져옵니다.
        UsersActivityProfile profile = usersActivityProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Activity profile not found for userId: " + userId));

        // DTO로 변환하여 반환
        return new ActivityProfileResponse(
                profile.getExerciseCount(),
                profile.getDietCount(),
                profile.getWeightCount()
        );
    }

}
