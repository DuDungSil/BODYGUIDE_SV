package org.bodyguide_sv.exercise.service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bodyguide_sv.exercise.controller.request.ExerciseRecordGroupRequest;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupListResponse;
import org.bodyguide_sv.exercise.dto.ExerciseAnalysisProfile;
import org.bodyguide_sv.exercise.dto.ExerciseRecordGroupDTO;
import org.bodyguide_sv.exercise.entity.UsersExerciseSetHistory;
import org.bodyguide_sv.exercise.event.ExerciseRecordChangedEvent;
import org.bodyguide_sv.exercise.repository.UsersExerciseSetHistoryRepository;
import org.bodyguide_sv.user.dto.UserProfileDTO;
import org.bodyguide_sv.user.service.UserProfileService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseRecordService {
    
    private final ApplicationEventPublisher eventPublisher;
    private final UsersExerciseSetHistoryRepository usersExerciseSetHistoryRepository; 
    private final ExerciseAnalysisService exerciseAnalysisService;
    private final UserProfileService userProfileService;

    // // 최근 n일치 ExerciseRecordGroup 조회
    // public List<ExerciseRecordGroupListResponse> fetchRecentDaysExerciseRecords(UUID userId, int days) {
    //     LocalDateTime startDate = LocalDateTime.now().minusDays(days);
    //     List<UsersExerciseSetHistory> entities = usersExerciseSetHistoryRepository
    //             .findByUserIdAndExerciseDateAfter(userId, startDate);
        
    //     return entities.stream().map(entity -> exerciseRecordMapper.toDTO(List.of(entity))).toList();
    // }

    // // 특정 yyyy년 mm월 조회 ( 페이지네이션 형식 )
    // public List<ExerciseRecordGroupListResponse> fetchMonthlyExerciseRecords(UUID userId, int year, int month, int page, int size) {
    //     Pageable pageable = PageRequest.of(page, size);
    //     List<UsersExerciseSetHistory> entities = usersExerciseSetHistoryRepository.findByUserIdAndExerciseDateBetween(
    //         userId,
    //         LocalDateTime.of(year, month, 1, 0, 0),
    //         LocalDateTime.of(year, month, YearMonth.of(year, month).lengthOfMonth(), 23, 59, 59),
    //         pageable
    //     ).toList();

    //     return entities.stream().map(entity -> exerciseRecordMapper.toDTO(List.of(entity))).toList();
    // }

    // save
    public void saveExerciseRecordGroup(UUID userId, ExerciseRecordGroupRequest request) {

        LocalDateTime exerciseDate = request.exerciseDate();

        // 마지막 그룹 id + 1
        int groupId = getMaxGroupId(userId, exerciseDate) + 1;

        // 각 ExerciseRecrodDTO 의 set에 대해 분석 서비스
        ExerciseRecordGroupDTO exerciseRecordGroupDTO = analyzeRequestToExerciseRecordGroupDTO(userId, groupId,
                request);

        // 엔티티 저장
        createExerciseRecords(userId, exerciseRecordGroupDTO);

        // 변경된 ExerciseId 리스트 추출
        List<Integer> changedExerciseIdList = exerciseRecordGroupDTO.recordGroup().stream()
                .map(ExerciseRecordGroupDTO.ExerciseRecrod::exerciseId)
                .distinct()
                .toList();

        // 이벤트 발행
        eventPublisher.publishEvent(new ExerciseRecordChangedEvent(userId, changedExerciseIdList));

    }

    // update
    public void updateExerciseRecordGroup(UUID userId, ExerciseRecordGroupRequest request) {

        LocalDateTime exerciseDate = request.exerciseDate();

        // 기존 그룹 id 사용
        int groupId = request.groupId();

        // 기존 데이터 삭제
        List<Integer> deletedExerciseIds = usersExerciseSetHistoryRepository
                .findExerciseIdsByUserIdAndExerciseDateAndGroupId(userId, exerciseDate, groupId);
        usersExerciseSetHistoryRepository.deleteByUserIdAndExerciseDateAndGroupId(userId, exerciseDate, groupId);

        // 새로운 데이터 저장
        ExerciseRecordGroupDTO exerciseRecordGroupDTO = analyzeRequestToExerciseRecordGroupDTO(userId, groupId, request);
        createExerciseRecords(userId, exerciseRecordGroupDTO);

        // 추가된 ExerciseId 리스트
        List<Integer> addedExerciseIds = exerciseRecordGroupDTO.recordGroup().stream()
                .map(ExerciseRecordGroupDTO.ExerciseRecrod::exerciseId)
                .distinct()
                .toList();

        // 변경된 ExerciseId 리스트
        List<Integer> changedExerciseIdList = Stream.concat(deletedExerciseIds.stream(), addedExerciseIds.stream())
                .distinct()
                .toList();

        // 이벤트 발행
        eventPublisher.publishEvent(new ExerciseRecordChangedEvent(userId, changedExerciseIdList));

    }

    // 삭제
    @Transactional
    public void deleteExerciseRecordGroup(UUID userId, LocalDateTime exerciseDate, int groupId) {
        // 삭제 대상 ExerciseId 가져오기
        List<Integer> deletedExerciseIds = usersExerciseSetHistoryRepository
                .findExerciseIdsByUserIdAndExerciseDateAndGroupId(userId, exerciseDate, groupId);

        // 데이터 삭제
        usersExerciseSetHistoryRepository.deleteByUserIdAndExerciseDateAndGroupId(userId, exerciseDate, groupId);

        // 이벤트 발행
        eventPublisher.publishEvent(new ExerciseRecordChangedEvent(userId, deletedExerciseIds));
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
