package org.bodyguide_sv.notification.enums;

import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationTemplate {
    
    WEIGHT_FIRST_RECORD(
        "첫 번째 체중을 기록했어요!",
        List.of()
    ),

    WEIGHT_COTINUOUS_RECORD(
        "#{date}일 연속 체중을 기록했어요!",
        List.of("date")
    ),

    WEIGHT_TARGET_COMPLETE(
        "목표 체중을 달성했어요!",
        List.of()
    ),
            
    WEIGHT_CHANGED(
        "체중이 #{changedWeight}kg #{changedString}했어요.",
        List.of("changedWeight", "changedString")
    ),
        
    EXERCISE_FIRST_RECORD(
        "첫 번째 운동을 기록했어요!",
        List.of()
    ),
        
    EXERCISE_COTINUOUS_RECORD(
        "#{date}일 연속 운동을 기록했어요!",
        List.of("date")
    ),
        
    EXERCISE_LEVEL_CHANGED(
        "운동 능력이 #{prevLevel}에서 #{changedLevel}로 향상되었어요.",
        List.of("prevLevel", "changedLevel")
    ),
        
    EXERCISE_DAILY_VOLUME(
        "오늘의 운동 볼륨은 #{volume}kg입니다. (#{signedChange}kg)",
        List.of("volume", "signedChange")
    );

    private final String message;
    private final List<String> requiredPlaceholders;

    
    public String getMessage() {
        return message;
    }

    public List<String> getRequiredPlaceholders() {
        return requiredPlaceholders;
    }

    // 메시지 생성 메서드
    public String generateMessage(Map<String, String> placeholders) {
        validatePlaceholders(placeholders);
        return applyPlaceholders(message, placeholders);
    }

    // 플레이스홀더 검증
    private void validatePlaceholders(Map<String, String> placeholders) {
        for (String placeholder : requiredPlaceholders) {
            if (!placeholders.containsKey(placeholder)) {
                throw new IllegalArgumentException("Missing placeholder: " + placeholder);
            }
        }
    }

    // 플레이스홀더 교체
    private String applyPlaceholders(String message, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace("#{" + entry.getKey() + "}", entry.getValue());
        }
        return message;
    }

}
