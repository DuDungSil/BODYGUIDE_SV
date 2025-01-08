package org.bodyguide_sv.exercise.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bodyguide_sv.exercise.controller.request.ExerciseRecordGroupRequest;
import org.bodyguide_sv.exercise.dto.ExerciseAnalysisProfile;
import org.bodyguide_sv.exercise.dto.ExerciseRecordGroupDTO;
import org.bodyguide_sv.exercise.entity.UsersExerciseSetHistory;
import org.bodyguide_sv.exercise.repository.UsersExerciseSetHistoryRepository;
import org.bodyguide_sv.user.dto.UserProfileDTO;
import org.bodyguide_sv.user.service.UserProfileService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseRecordService {
    
    private final UsersExerciseSetHistoryRepository usersExerciseSetHistoryRepository; 
    private final ExerciseAnalysisService exerciseAnalysisService;
    private final UserProfileService userProfileService;

    // 최근 n일치 ExerciseRecordGroup 조회

    // 특정 yyyy년 mm월 조회 ( 페이지네이션 형식식 )

    // save
    public void saveExerciseRecordGroup(UUID userId, ExerciseRecordGroupRequest request) {

        LocalDateTime exerciseDate = request.exerciseDate();

        // 마지막 그룹 id + 1
        int groupId = getMaxGroupId(userId, exerciseDate) + 1;

        // 각 ExerciseRecrodDTO 의 set에 대해 분석 서비스
        ExerciseRecordGroupDTO exerciseRecordGroupDTO = analyzeRequestToExerciseRecordGroupDTO(userId, groupId,
                request);

        // 생성
        createExerciseRecords(userId, exerciseRecordGroupDTO);

    }

    // update
    public void updateExerciseRecordGroup(UUID userId, ExerciseRecordGroupRequest request) {

        LocalDateTime exerciseDate = request.exerciseDate();

        // 기존 그룹 id 사용
        int groupId = request.groupId();

        // 각 ExerciseRecrodDTO 의 set에 대해 분석 서비스
        ExerciseRecordGroupDTO exerciseRecordGroupDTO = analyzeRequestToExerciseRecordGroupDTO(userId, groupId, request);

        // 기존 그룹 삭제
        deleteExerciseRecordGroup(userId, exerciseDate, groupId);

        // 생성
        createExerciseRecords(userId, exerciseRecordGroupDTO);

    }

    // 삭제
    @Transactional
    public void deleteExerciseRecordGroup(UUID userId, LocalDateTime exerciseDate, int groupId) {
        usersExerciseSetHistoryRepository.deleteByUserIdAndExerciseDateAndGroupId(userId, exerciseDate, groupId);
    }

    // date 같은 max 그룹id 반환
    private int getMaxGroupId(UUID userId, LocalDateTime exerciseDate) {
        Integer maxGroupId = usersExerciseSetHistoryRepository.findMaxGroupIdByUserIdAndExerciseDate(userId, exerciseDate);
        return maxGroupId != null ? maxGroupId : 0;
    }

    // request -> 분석 -> DTO
    private ExerciseRecordGroupDTO analyzeRequestToExerciseRecordGroupDTO(UUID userId, int groupId,
            ExerciseRecordGroupRequest request) {
        UserProfileDTO userProfile = userProfileService.getUserProfileDTO(userId);

        String gender = userProfile.gender();
        Double bodyWeight = userProfile.weight();

        return new ExerciseRecordGroupDTO(
                groupId,
                request.exerciseDate(),
                request.exercises().stream()
                        .map(exerciseRequest -> {
                            int exerciseId = exerciseRequest.exerciseId();
                            return new ExerciseRecordGroupDTO.ExerciseRecrod(
                                    exerciseId,
                                    exerciseRequest.sets().stream()
                                            .map(setRequest -> {
                                                double liftingWeight = setRequest.weight();
                                                int reps = setRequest.reps();

                                                // 분석 서비스
                                                ExerciseAnalysisProfile analysisProfile = exerciseAnalysisService
                                                        .analyzeExercise(exerciseId, gender, bodyWeight, liftingWeight,
                                                                reps);

                                                double score = analysisProfile.getScore();
                                                double strength = analysisProfile.getStrength();

                                                return new ExerciseRecordGroupDTO.ExerciseSet(
                                                        setRequest.set(),
                                                        setRequest.weight(),
                                                        setRequest.reps(),
                                                        score,
                                                        strength);
                                            })
                                            .toList());
                        })
                        .toList());
    }
    
    // DTO -> 엔티티 저장
    private void createExerciseRecords(UUID userId, ExerciseRecordGroupDTO exerciseRecordGroupDTO) {
        List<UsersExerciseSetHistory> histories = exerciseRecordGroupDTO.recordGroup().stream()
                .flatMap(exerciseRecord -> exerciseRecord.set().stream()
                        .map(set -> UsersExerciseSetHistory.builder()
                                .userId(userId)
                                .groupId(exerciseRecordGroupDTO.groupId())
                                .exerciseDate(exerciseRecordGroupDTO.exerciseDate())
                                .exerciseId(exerciseRecord.exerciseId())
                                .set(set.set())
                                .weight(set.weight())
                                .reps(set.reps())
                                .score(set.score())
                                .strength(set.strength())
                                .updatedAt(LocalDateTime.now())
                                .build()))
                .toList();

        usersExerciseSetHistoryRepository.saveAll(histories);
    }

    // 엔티티 -> DTO
    public ExerciseRecordGroupDTO toDTO(List<UsersExerciseSetHistory> entities) {
        return entities.stream()
            .collect(Collectors.groupingBy(UsersExerciseSetHistory::getGroupId))
            .entrySet().stream()
            .map(entry -> new ExerciseRecordGroupDTO(
                entry.getKey(),
                entry.getValue().get(0).getExerciseDate(),
                entry.getValue().stream().collect(Collectors.groupingBy(UsersExerciseSetHistory::getExerciseId))
                    .entrySet().stream()
                    .map(exerciseEntry -> new ExerciseRecordGroupDTO.ExerciseRecrod(
                        exerciseEntry.getKey(),
                        exerciseEntry.getValue().stream()
                            .map(history -> new ExerciseRecordGroupDTO.ExerciseSet(
                                history.getSet(),
                                history.getWeight(),
                                history.getReps(),
                                history.getScore(),
                                history.getStrength()
                            ))
                            .toList()
                    ))
                    .toList()
            ))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No records found"));
    }

}
