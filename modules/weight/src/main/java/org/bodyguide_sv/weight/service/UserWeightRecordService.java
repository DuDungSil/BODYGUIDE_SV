package org.bodyguide_sv.weight.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bodyguide_sv.weight.controller.request.WeightRecordRequest;
import org.bodyguide_sv.weight.controller.response.WeightRecordSliceResponse;
import org.bodyguide_sv.weight.controller.response.WeightRecordSliceResponse.WeightRecordResponse;
import org.bodyguide_sv.weight.entity.UsersWeightHistory;
import org.bodyguide_sv.weight.event.NewWeightRecordSavedEvent;
import org.bodyguide_sv.weight.event.WeightUpdatedEvent;
import org.bodyguide_sv.weight.repository.UsersWeightHistoryRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserWeightRecordService {
    
    private final ApplicationEventPublisher eventPublisher;
    private final UsersWeightHistoryRepository usersWeightHistoryRepository;

    // 조회
    public WeightRecordSliceResponse fetchWeightRecord(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("recordDate").descending());
        Slice<UsersWeightHistory> recordsSlice = usersWeightHistoryRepository.findByUserId(userId, pageable);

        List<WeightRecordResponse> weightRecordList = recordsSlice.getContent().stream()
            .map(record -> new WeightRecordResponse(
                record.getHistoryId(),
                record.getWeight(),
                record.getRecordDate()
            ))
            .collect(Collectors.toList());

        return new WeightRecordSliceResponse(
            page,
            size,
            recordsSlice.hasNext(),  
            weightRecordList
        );
    }

    // 저장
    @Transactional
    public void saveWeightRecord(UUID userId, WeightRecordRequest request) {
        double newWeight = request.weight();

        // UsersWeightHistory 엔티티 생성
        UsersWeightHistory weightRecord = UsersWeightHistory.builder()
                .userId(userId)
                .weight(newWeight)
                .recordDate(request.recordAt() != null ? request.recordAt() : LocalDateTime.now())
                .build();

        // 저장
        usersWeightHistoryRepository.save(weightRecord);

        // 이벤트 발행
        eventPublisher.publishEvent(new WeightUpdatedEvent(userId));

        eventPublisher.publishEvent(new NewWeightRecordSavedEvent(userId));
    }

    // 삭제
    @Transactional
    public void deleteWeightRecord(UUID userId, Long historyId) {
        try {
            // 먼저 userId와 historyId가 모두 일치하는 레코드가 존재하는지 확인
            boolean exists = usersWeightHistoryRepository.existsByHistoryIdAndUserId(historyId, userId);
            if (!exists) {
                throw new IllegalArgumentException("No weight record found with historyId: " + historyId + " and userId: " + userId);
            }
            // 레코드가 존재하면 삭제
            usersWeightHistoryRepository.deleteById(historyId);
            
            // 이벤트 발행
            eventPublisher.publishEvent(new WeightUpdatedEvent(userId));

        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("No weight record found with historyId: " + historyId, e);
        }
    }
}
