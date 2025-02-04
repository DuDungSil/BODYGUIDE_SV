package org.bodyguide_sv.exercise.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.UUID;

import org.bodyguide_sv.exercise.controller.response.ExerciseVolumeDailySliceResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseVolumeDailySliceResponse.ExerciseVolumeDailyResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseVolumeMonthlySliceResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseVolumeWeeklySliceResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseVolumeWeeklySliceResponse.ExerciseVolumeWeeklyResponse;
import org.bodyguide_sv.exercise.entity.UsersExerciseVolumeDaily;
import org.bodyguide_sv.exercise.entity.UsersExerciseVolumeMonthly;
import org.bodyguide_sv.exercise.entity.UsersExerciseVolumeWeekly;
import org.bodyguide_sv.exercise.repository.UsersExerciseVolumeDailyRepository;
import org.bodyguide_sv.exercise.repository.UsersExerciseVolumeMonthlyRepository;
import org.bodyguide_sv.exercise.repository.UsersExerciseVolumeWeeklyRepository;
import org.bodyguide_sv.exercise.repository.custom.UsersExerciseSetHistoryCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExerciseVolumeService {
    
    private final UsersExerciseSetHistoryCustomRepository usersExerciseSetHistoryCustomRepository;
    private final UsersExerciseVolumeDailyRepository usersExerciseVolumeDailyRepository;
    private final UsersExerciseVolumeWeeklyRepository usersExerciseVolumeWeeklyRepository;
    private final UsersExerciseVolumeMonthlyRepository usersExerciseVolumeMonthlyRepository;

    // 일별 볼륨 조회 ( 슬라이싱 )
    public ExerciseVolumeDailySliceResponse getVolumeDailySliceResponse(UUID userId, int page, int size) {
        // 페이지네이션 설정
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "date"));

        // 사용자 데이터 조회 (페이징 처리)
        Page<UsersExerciseVolumeDaily> dailyVolumes = usersExerciseVolumeDailyRepository.findByUserId(userId, pageable);

        // 조회된 데이터를 Response 형태로 변환
        List<ExerciseVolumeDailyResponse> responseList = dailyVolumes
            .getContent()
            .stream()
            .map(daily -> new ExerciseVolumeDailyResponse(
                daily.getId(),
                daily.getDate(),
                daily.getVolume()
            ))
            .toList();

        // Response 반환
        return new ExerciseVolumeDailySliceResponse(
                dailyVolumes.getNumber(),
                dailyVolumes.getSize(),
                dailyVolumes.hasNext(),
                responseList
            );
    }

    // 주별 볼륨 조회 ( 슬라이싱 )
    public ExerciseVolumeWeeklySliceResponse getVolumeWeeklySliceResponse(UUID userId, int page, int size) {
        // 페이지네이션 설정
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "year").and(Sort.by(Sort.Direction.DESC, "week")));

        // 사용자 데이터 조회 (페이징 처리)
        Page<UsersExerciseVolumeWeekly> weeklyVolumes = usersExerciseVolumeWeeklyRepository.findByUserId(userId, pageable);

        // 조회된 데이터를 Response 형태로 변환
        List<ExerciseVolumeWeeklyResponse> responseList = weeklyVolumes
            .getContent()
            .stream()
            .map(weekly -> new ExerciseVolumeWeeklyResponse(
                weekly.getId(),
                weekly.getYear(),
                weekly.getWeek(),
                weekly.getVolume()
            ))
            .toList();

        // Response 반환
        return new ExerciseVolumeWeeklySliceResponse(
            weeklyVolumes.getNumber(),  // 현재 페이지 번호
            weeklyVolumes.getSize(),    // 페이지당 항목 수
            weeklyVolumes.hasNext(),    // 다음 페이지 여부
            responseList                // 데이터 리스트
        );
    }

    // 월별 볼륨 조회 ( 슬라이싱 )
    public ExerciseVolumeMonthlySliceResponse getVolumeMonthlySliceResponse(UUID userId, int page, int size) {
        // 페이지네이션 설정
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "year").and(Sort.by(Sort.Direction.DESC, "month")));

        // 사용자 데이터 조회 (페이징 처리)
        Page<UsersExerciseVolumeMonthly> monthlyVolumes = usersExerciseVolumeMonthlyRepository.findByUserId(userId, pageable);

        // 조회된 데이터를 Response 형태로 변환
        List<ExerciseVolumeMonthlySliceResponse.ExerciseVolumeMonthlyResponse> responseList = monthlyVolumes
            .getContent()
            .stream()
            .map(monthly -> new ExerciseVolumeMonthlySliceResponse.ExerciseVolumeMonthlyResponse(
                monthly.getId(),
                monthly.getYear(),
                monthly.getMonth(),
                monthly.getVolume()
            ))
            .toList();

        // Response 반환
        return new ExerciseVolumeMonthlySliceResponse(
            monthlyVolumes.getNumber(),  // 현재 페이지 번호
            monthlyVolumes.getSize(),    // 페이지당 항목 수
            monthlyVolumes.hasNext(),    // 다음 페이지 여부
            responseList                 // 데이터 리스트
        );
    }

    // 업데이트
    @Transactional
    public void updateExerciseVolume(UUID userId, LocalDateTime date) {

        // Step 1: 일별 데이터 갱신
        updateDailyVolume(userId, date);

        // Step 2: 주별 데이터 갱신
        updateWeeklyVolume(userId, date);

        // Step 3: 월별 데이터 갱신
        updateMonthlyVolume(userId, date);

    }

    private void updateDailyVolume(UUID userId, LocalDateTime date) {
        LocalDate localDate = date.toLocalDate();

        // 해당 날짜의 운동 기록을 가져와 총합 계산
        double dailyVolume = usersExerciseSetHistoryCustomRepository
                .findByUserIdAndExerciseDate(userId, localDate)
                .stream()
                .mapToDouble(history -> history.getWeight() * history.getReps())
                .sum();

        // 기존 데이터를 조회하거나 새로 생성
        UsersExerciseVolumeDaily dailyRecord = usersExerciseVolumeDailyRepository
                .findByUserIdAndDate(userId, localDate)
                .orElse(UsersExerciseVolumeDaily.create(userId, localDate));

        // 전체 볼륨으로 갱신
        dailyRecord.updateVolume(dailyVolume);
        usersExerciseVolumeDailyRepository.save(dailyRecord);
    }

    private void updateWeeklyVolume(UUID userId, LocalDateTime date) {
        int year = date.getYear();
        int week = date.get(WeekFields.ISO.weekOfYear());

        // 해당 주의 운동 기록을 가져와 총합 계산
        double weeklyVolume = usersExerciseSetHistoryCustomRepository
                .findByUserIdAndYearAndWeek(userId, year, week)
                .stream()
                .mapToDouble(history -> history.getWeight() * history.getReps())
                .sum();

        // 기존 데이터를 조회하거나 새로 생성
        UsersExerciseVolumeWeekly weeklyRecord = usersExerciseVolumeWeeklyRepository
                .findByUserIdAndYearAndWeek(userId, year, week)
                .orElse(UsersExerciseVolumeWeekly.create(userId, year, week));

        // 전체 볼륨으로 갱신
        weeklyRecord.updateVolume(weeklyVolume);
        usersExerciseVolumeWeeklyRepository.save(weeklyRecord);
    } 

    private void updateMonthlyVolume(UUID userId, LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonthValue();

        // 해당 월의 운동 기록을 가져와 총합 계산
        double monthlyVolume = usersExerciseSetHistoryCustomRepository
            .findByUserIdAndYearAndMonth(userId, year, month)
            .stream()
            .mapToDouble(history -> history.getWeight() * history.getReps())
            .sum();

        // 기존 데이터를 조회하거나 새로 생성
        UsersExerciseVolumeMonthly monthlyRecord = usersExerciseVolumeMonthlyRepository
            .findByUserIdAndYearAndMonth(userId, year, month)
            .orElse(UsersExerciseVolumeMonthly.create(userId, year, month));

        // 전체 볼륨으로 갱신
        monthlyRecord.updateVolume(monthlyVolume);
        usersExerciseVolumeMonthlyRepository.save(monthlyRecord);
    }

}
