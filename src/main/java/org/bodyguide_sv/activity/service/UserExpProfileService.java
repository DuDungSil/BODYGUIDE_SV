package org.bodyguide_sv.activity.service;

import java.util.UUID;

import org.bodyguide_sv.activity.dto.ExpProfileResponse;
import org.bodyguide_sv.activity.entity.LevelInfo;
import org.bodyguide_sv.activity.entity.UsersExpProfile;
import org.bodyguide_sv.activity.repository.UsersExpProfileRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserExpProfileService {
    
    private final UsersExpProfileRepository usersExpProfileRepository;
    private final LevelInfoService levelInfoService;

    // 새로운 유저 ExpProfile 생성
    public UsersExpProfile createUserExpProfile(UUID userId) {
        UsersExpProfile newProfile = UsersExpProfile.create(userId);
        return usersExpProfileRepository.save(newProfile);
    }

    // ExpProfileResponse 생성
    public ExpProfileResponse getExpProfileResponse(UUID userId) {

        // 유저 exp 프로필 조회
        UsersExpProfile userExpProfile = getUserExpProfile(userId);
        int UserCurrentLevel = userExpProfile.getCurrentLevel();
        int UsertotalExp = userExpProfile.getTotalExp();

        // 현재 레벨 정보 가져오기
        LevelInfo currentLevelInfo = levelInfoService.getLevelInfo(UserCurrentLevel);
        int nextLevelExp = currentLevelInfo.getNextLevelExp();
        int totalExp = currentLevelInfo.getTotalExp();

        // 현재 경험치 계산
        int currentExp = UsertotalExp - (totalExp - nextLevelExp);

        int requiredExp = nextLevelExp;
        if (UserCurrentLevel == levelInfoService.getMaxLevel()) {
            requiredExp = 0;
        }

        return ExpProfileResponse.builder()
                .level(userExpProfile.getCurrentLevel())
                .currentExp(currentExp)
                .requiredExp(requiredExp)
                .build();
                
    }

    // 사용자 경험치 프로필 조회 (존재하지 않으면 예외 발생)
    public UsersExpProfile getUserExpProfile(UUID userId) {
        return usersExpProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("UsersExpProfile not found for userId: " + userId));
    }

    // 사용자 경험치 프로필 업데이트
    public void updateUserExpProfile(UsersExpProfile updatedProfile) {
        usersExpProfileRepository.save(updatedProfile);
    }

}