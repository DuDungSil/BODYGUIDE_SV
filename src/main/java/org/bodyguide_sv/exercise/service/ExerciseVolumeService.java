package org.bodyguide_sv.exercise.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.UUID;

import org.bodyguide_sv.exercise.entity.UsersExerciseVolumeDaily;
import org.bodyguide_sv.exercise.entity.UsersExerciseVolumeMonthly;
import org.bodyguide_sv.exercise.entity.UsersExerciseVolumeWeekly;
import org.bodyguide_sv.exercise.repository.UsersExerciseSetHistoryRepository;
import org.bodyguide_sv.exercise.repository.UsersExerciseVolumeDailyRepository;
import org.bodyguide_sv.exercise.repository.UsersExerciseVolumeMonthlyRepository;
import org.bodyguide_sv.exercise.repository.UsersExerciseVolumeWeeklyRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ExerciseVolumeService {
    
    private final UsersExerciseSetHistoryRepository usersExerciseSetHistoryRepository;
    private final UsersExerciseVolumeDailyRepository usersExerciseVolumeDailyRepository;
    private final UsersExerciseVolumeWeeklyRepository usersExerciseVolumeWeeklyRepository;
    private final UsersExerciseVolumeMonthlyRepository usersExerciseVolumeMonthlyRepository;

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
        double dailyVolume = usersExerciseSetHistoryRepository
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
        double weeklyVolume = usersExerciseSetHistoryRepository
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
        double monthlyVolume = usersExerciseSetHistoryRepository
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
