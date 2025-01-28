package org.bodyguide_sv.weight.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.weight.dto.UserWeightProfileDTO;
import org.bodyguide_sv.weight.entity.UsersWeightHistory;
import org.bodyguide_sv.weight.entity.UsersWeightProfile;
import org.bodyguide_sv.weight.repository.UsersWeightHistoryRepository;
import org.bodyguide_sv.weight.repository.UsersWeightProfileRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserWeightProfileService {
    
    private final UsersWeightProfileRepository usersWeightProfileRepository;
    private final UsersWeightHistoryRepository usersWeightHistoryRepository;

    // dto 가져오기
    public UserWeightProfileDTO getWeightProfile(UUID userId) {
        // 유저의 Weight Profile 가져오기 또는 생성
        UsersWeightProfile profile = usersWeightProfileRepository.findByUserId(userId)
                .orElse(UsersWeightProfile.create(userId));
        return new UserWeightProfileDTO(profile.getWeight(), profile.getRecordDate());
    }

    // 업데이트
    @Transactional
    public void updateWeightProfile(UUID userId) {

        // 유저의 Weight Profile 가져오기 또는 생성
        UsersWeightProfile profile = usersWeightProfileRepository.findByUserId(userId)
                .orElse(UsersWeightProfile.create(userId));

        // 유저의 최신 체중 조회 (null 처리 포함)
        UsersWeightHistory usersWeightHistory = usersWeightHistoryRepository
                .findTopByUserIdOrderByRecordDateDesc(userId)
                .orElse(null);

        // 최신 체중이 없으면 null, 있으면 체중 값으로 업데이트
        Double newWeight = (usersWeightHistory != null) ? usersWeightHistory.getWeight() : null;
        LocalDateTime recordedDate = (usersWeightHistory != null) ? usersWeightHistory.getRecordDate() : null;

        // 프로필 업데이트
        profile.updateWeight(newWeight, recordedDate);

        // 저장
        usersWeightProfileRepository.save(profile);

    }

    // 생성
    public void createUsersWeightProfile(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("userId must not be null when creating a exercise profile.");
        }
        
        UsersWeightProfile newProfile = UsersWeightProfile.create(userId);

        usersWeightProfileRepository.save(newProfile);
    }

}
