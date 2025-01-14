package org.bodyguide_sv.exercise.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bodyguide_sv.exercise.controller.request.ExerciseRecordGroupRequest;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseRecordGroupListResponseWithHasNext;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseRecordGroupResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseRecordResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse.ExerciseSetResponse;
import org.bodyguide_sv.exercise.dto.ExerciseAnalysisProfile;
import org.bodyguide_sv.exercise.dto.ExerciseRecordGroupDTO;
import org.bodyguide_sv.exercise.entity.UsersExerciseSetHistory;
import org.bodyguide_sv.exercise.event.ExerciseRecordChangedEvent;
import org.bodyguide_sv.exercise.event.NewExerciseRecordSavedEvent;
import org.bodyguide_sv.exercise.repository.ExerciseQueryRepository;
import org.bodyguide_sv.exercise.repository.UsersExerciseSetHistoryRepository;
import org.bodyguide_sv.user.dto.UserProfileDTO;
import org.bodyguide_sv.user.service.UserProfileService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExerciseRecordService {
    
    private final ApplicationEventPublisher eventPublisher;
	private final ExerciseQueryRepository exerciseQueryRepository;
    private final UsersExerciseSetHistoryRepository usersExerciseSetHistoryRepository; 
    private final ExerciseAnalysisService exerciseAnalysisService;
    private final UserProfileService userProfileService;

    // 최근 n일치 ExerciseRecordGroup 조회
    public ExerciseRecordGroupSliceResponse fetchRecentDaysExerciseRecords(UUID userId, int days, int page, int size) {

        ExerciseRecordGroupListResponseWithHasNext listWithHasNext = exerciseQueryRepository.fetchRecentDaysExerciseRecords(userId, days, page, size);

        return new ExerciseRecordGroupSliceResponse(page, size, listWithHasNext.hasNext(), toGroupRecords(listWithHasNext.recordGroupList()));
    }

    // 특정 yyyy년 mm월 조회 
    public ExerciseRecordGroupSliceResponse fetchMonthlyExerciseRecords(UUID userId, int year, int month, int page, int size) {

        ExerciseRecordGroupListResponseWithHasNext listWithHasNext = exerciseQueryRepository.fetchMonthlyExerciseRecords(userId, year,
                month, page, size);

        return new ExerciseRecordGroupSliceResponse(page, size, listWithHasNext.hasNext(), toGroupRecords(listWithHasNext.recordGroupList()));
    }

    // 저장
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

        // 이벤트 발행
        eventPublisher.publishEvent(new NewExerciseRecordSavedEvent(userId));

    }

    // 업데이트
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

                                                // 운동 분석 
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
                                                        history.getStrength()))
                                                .toList()))
                                .toList()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No records found"));
    }

    // ExerciseRecordGroupResponse 그룹화
    public List<ExerciseRecordGroupResponse> toGroupRecords(List<ExerciseRecordGroupResponse> originalResponseList) {
        
        // Map to hold grouped exercises
        Map<Integer, Map<LocalDateTime, Map<Integer, List<ExerciseRecordGroupSliceResponse.ExerciseRecordResponse>>>> groupedMap = new HashMap<>();

        // Iterate through each group in the original response
        for (ExerciseRecordGroupResponse group : originalResponseList) {
            int groupId = group.groupId();
            LocalDateTime exerciseDate = group.exerciseDate();

            groupedMap.putIfAbsent(groupId, new HashMap<>());
            Map<LocalDateTime, Map<Integer, List<ExerciseRecordResponse>>> dateMap = groupedMap.get(groupId);

            dateMap.putIfAbsent(exerciseDate, new HashMap<>());
            Map<Integer, List<ExerciseRecordResponse>> exerciseMap = dateMap.get(exerciseDate);

            for (ExerciseRecordResponse exercise : group.exercises()) {
                exerciseMap.putIfAbsent(exercise.exerciseId(), new ArrayList<>());
                exerciseMap.get(exercise.exerciseId()).add(exercise);
            }
        }

        // Convert the grouped map back into the response structure
        List<ExerciseRecordGroupResponse> groupedResponseList = groupedMap.entrySet().stream()
            .flatMap(groupEntry -> groupEntry.getValue().entrySet().stream()
                .map(dateEntry -> new ExerciseRecordGroupSliceResponse.ExerciseRecordGroupResponse(
                    groupEntry.getKey(),
                    dateEntry.getKey(),
                    dateEntry.getValue().entrySet().stream()
                        .map(exerciseEntry -> new ExerciseRecordGroupSliceResponse.ExerciseRecordResponse(
                            exerciseEntry.getKey(),
                            exerciseEntry.getValue().stream()
                                .flatMap(exercise -> exercise.sets().stream())
                                .sorted(Comparator.comparingInt(ExerciseSetResponse::set))
                                .collect(Collectors.toList()),
                            exerciseEntry.getValue().get(0).prevBestWeight(),
                            exerciseEntry.getValue().get(0).prevBestReps()
                        ))
                        .collect(Collectors.toList())
                ))
            )
            .collect(Collectors.toList());

        return groupedResponseList;
    }

}
