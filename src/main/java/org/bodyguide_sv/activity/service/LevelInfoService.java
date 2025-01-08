package org.bodyguide_sv.activity.service;

import java.util.List;

import org.bodyguide_sv.activity.entity.LevelInfo;
import org.bodyguide_sv.activity.repository.LevelInfoRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LevelInfoService {
    
    private final LevelInfoRepository levelInfoRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String REDIS_KEY = "level_info";
    private static final int MAX_LEVEL = 30;

    // 레벨 정보 캐싱
    @PostConstruct
    public void cacheLevelInfo() {
        // 1. DB에서 모든 LevelInfo 데이터를 가져옴
        List<LevelInfo> levelInfos = levelInfoRepository.findAll();

        // 2. Redis에 저장
        for (LevelInfo levelInfo : levelInfos) {
            String key = REDIS_KEY + ":" + levelInfo.getLevel();
            redisTemplate.opsForValue().set(key, levelInfo);
        }

        System.out.println("LevelInfo data cached into Redis successfully!");
    }

    // Redis에서 LevelInfo를 가져오는 메서드
    public LevelInfo getLevelInfo(int level) {
        String key = REDIS_KEY + ":" + level;
        return (LevelInfo) redisTemplate.opsForValue().get(key);
    }

    public int getMaxLevel() {
        return MAX_LEVEL;
    }

}
