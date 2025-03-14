package org.bodyguide_sv.exercise.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.exercise.controller.request.ExerciseRecordGroupRequest;
import org.bodyguide_sv.exercise.controller.response.ExerciseBigThreeProfileResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseRecordGroupSliceResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseReportResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseTotalPerformanceResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseVolumeDailySliceResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseVolumeMonthlySliceResponse;
import org.bodyguide_sv.exercise.controller.response.ExerciseVolumeWeeklySliceResponse;
import org.bodyguide_sv.exercise.service.ExerciseRecordService;
import org.bodyguide_sv.exercise.service.ExerciseReportService;
import org.bodyguide_sv.exercise.service.ExerciseVolumeService;
import org.bodyguide_sv.exercise.service.UserBigThreeProfileService;
import org.bodyguide_sv.exercise.service.UserExerciseTotalPerformanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exercise")
@Tag(name = "Exercise", description = "운동 관련")
public class ExerciseController {
    
    private final ExerciseVolumeService exerciseVolumeService;
    private final UserExerciseTotalPerformanceService userExerciseTotalPerformanceService;
    private final UserBigThreeProfileService userBigThreeProfileService;
    private final ExerciseRecordService exerciseRecordService;
    private final ExerciseReportService exerciseReportService;

    @PostMapping("/record")
    @Operation(summary = "유저 운동 기록 저장", description = "유저 운동 기록 저장 하기")
    public ResponseEntity<String> saveExerciseRecord(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ExerciseRecordGroupRequest request) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        exerciseRecordService.saveExerciseRecordGroup(userId, request);
        return ResponseEntity.ok("저장 완료");
    }

    @PostMapping("/record/update")
    @Operation(summary = "유저 운동 기록 업데이트", description = "유저 운동 기록 업데이트(수정) 하기")
    public ResponseEntity<String> updateExerciseRecordGroup(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ExerciseRecordGroupRequest request) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        exerciseRecordService.updateExerciseRecordGroup(userId, request);
        return ResponseEntity.ok("수정 완료");
    }

    @DeleteMapping("/record")
    @Operation(summary = "유저 운동 기록 삭제", description = "유저 운동 기록 삭제 하기")
    public ResponseEntity<String> deleteExerciseRecordGroup(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam LocalDateTime exerciseDate, @RequestParam int groupId) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        exerciseRecordService.deleteExerciseRecordGroup(userId, exerciseDate, groupId);
        return ResponseEntity.ok("삭제 완료");
    }

    @GetMapping("/record/recentDay")
    @Operation(summary = "최근 n 일 유저 운동 기록 조회", description = "최근 n 일 유저 운동 기록 조회 하기")
    public ResponseEntity<ExerciseRecordGroupSliceResponse> getExerciseRecordGroupListRecentDays(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam int days, @RequestParam int page, @RequestParam int size) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        ExerciseRecordGroupSliceResponse response = exerciseRecordService.fetchRecentDaysExerciseRecords(userId, days, page, size);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/record/month")
    @Operation(summary = "운동 기록 조회 ( yyyy년 mm월 조회 )", description = "운동 기록 조회 ( yyyy년 mm월 조회 )")
    public ResponseEntity<ExerciseRecordGroupSliceResponse> getExerciseRecordGroupListMonthly(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam int year, @RequestParam int month, @RequestParam int page, @RequestParam int size) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        ExerciseRecordGroupSliceResponse response = exerciseRecordService.fetchMonthlyExerciseRecords(userId, year, month, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bigthree")
    @Operation(summary = "유저 3대 운동 프로필 조회", description = "유저 3대 운동 프로필 조회")
    public ResponseEntity<ExerciseBigThreeProfileResponse> getExerciseBigThreeProfile(
            @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        ExerciseBigThreeProfileResponse response = userBigThreeProfileService.getBigThreeProfileResponse(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/performance")
    @Operation(summary = "유저 통합 기록 조회", description = "유저 통합 기록 조회")
    public ResponseEntity<ExerciseTotalPerformanceResponse> getExerciseTotalPerformance(
            @AuthenticationPrincipal UserDetails userDetails) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        ExerciseTotalPerformanceResponse response = userExerciseTotalPerformanceService
                .getTotalPerformanceResponse(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/volume/daily")
    @Operation(summary = "일별 운동 볼륨 조회", description = "일별 운동 볼륨 조회")
    public ResponseEntity<ExerciseVolumeDailySliceResponse> getExerciseVolumeDaily(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam int page, @RequestParam int size) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        ExerciseVolumeDailySliceResponse response = exerciseVolumeService
                .getVolumeDailySliceResponse(userId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/volume/weekly")
    @Operation(summary = "주별 운동 볼륨 조회", description = "주별 운동 볼륨 조회")
    public ResponseEntity<ExerciseVolumeWeeklySliceResponse> getExerciseVolumeWeekly(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam int page, @RequestParam int size) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        ExerciseVolumeWeeklySliceResponse response = exerciseVolumeService
                .getVolumeWeeklySliceResponse(userId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/volume/monthly")
    @Operation(summary = "월별 운동 볼륨 조회", description = "월별 운동 볼륨 조회")
    public ResponseEntity<ExerciseVolumeMonthlySliceResponse> getExerciseVolumeMonthly(
            @AuthenticationPrincipal UserDetails userDetails, @RequestParam int page, @RequestParam int size) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        ExerciseVolumeMonthlySliceResponse response = exerciseVolumeService
                .getVolumeMonthlySliceResponse(userId, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/analysis/report")
    @Operation(summary = "운동 분석 리포트 결과", description = "DB의 유저 프로필 정보들을 통해 운동 분석 결과를 조회")
    public ResponseEntity<ExerciseReportResponse> getExerciseReport(@AuthenticationPrincipal UserDetails userDetails) {

        UUID userId = UUID.fromString(userDetails.getUsername());
        ExerciseReportResponse exerciseReportResponse = exerciseReportService
                .getReportResponse(userId);
        
        return ResponseEntity.ok(exerciseReportResponse);
    }
    

}