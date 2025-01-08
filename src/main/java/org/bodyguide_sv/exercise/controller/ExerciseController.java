package org.bodyguide_sv.exercise.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.bodyguide_sv.exercise.controller.request.ExerciseRecordGroupRequest;
import org.bodyguide_sv.exercise.controller.request.UserExerciseStatsRequest;
import org.bodyguide_sv.exercise.controller.response.ExerciseReportResponse;
import org.bodyguide_sv.exercise.controller.response.UserExerciseStatsResponse;
import org.bodyguide_sv.exercise.service.ExerciseRecordService;
import org.bodyguide_sv.exercise.service.ExerciseReportService;
import org.bodyguide_sv.exercise.service.UserExerciseStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    
    private final ExerciseRecordService exerciseRecordService;
    private final UserExerciseStatsService userExerciseStatsService;
    private final ExerciseReportService exerciseReportService;

    // 운동 기록 save
    @PostMapping("/record")
    @Operation(summary = "유저 운동 기록 저장", description = "유저 운동 기록 저장 하기")
    public ResponseEntity<String> saveExerciseRecord(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ExerciseRecordGroupRequest request) {
        exerciseRecordService.saveExerciseRecordGroup(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok("저장 완료");
    }

    // 운동 기록 update
    @PostMapping("/record/update")
    @Operation(summary = "유저 운동 기록 업데이트", description = "유저 운동 기록 업데이트(수정) 하기")
    public ResponseEntity<String> updateExerciseRecordGroup(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ExerciseRecordGroupRequest request) {
        exerciseRecordService.updateExerciseRecordGroup(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok("수정 완료");
    }

    // 운동 기록 삭제
    @DeleteMapping("/record")
    @Operation(summary = "유저 운동 기록 삭제", description = "유저 운동 기록 삭제 하기")
    public ResponseEntity<String> deleteExerciseRecordGroup(@AuthenticationPrincipal UserDetails userDetails, @RequestParam LocalDateTime exerciseDate, @RequestParam int groupId) {
        exerciseRecordService.deleteExerciseRecordGroup(UUID.fromString(userDetails.getUsername()), exerciseDate, groupId);
        return ResponseEntity.ok("삭제 완료");
    }

    // 운동 기록 조회 ( 최근 n 일 )
    
    // 운동 기록 조회 ( yyyy년 mm월 조회 )

    // 유저 운동 통합 기록 조회

    // 3대 운동 조회

    @GetMapping("/stats")
    @Operation(summary = "유저 운동 스탯 조회", description = "유저 운동 스탯 정보를 조회")
    public ResponseEntity<UserExerciseStatsResponse> getExerciseProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserExerciseStatsResponse userExerciseProfileResponse = userExerciseStatsService
                .getUserExerciseProfileResponse(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(userExerciseProfileResponse);
    }

    @PatchMapping("/stats")
    @Operation(summary = "유저 운동 스탯 부분 업데이트", description = "유저 운동 스탯 정보를 부분 업데이트")
    public ResponseEntity<String> updateExerciseProfile(@AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UserExerciseStatsRequest request) {
        userExerciseStatsService.updateUserExerciseProfile(UUID.fromString(userDetails.getUsername()), request);
        return ResponseEntity.ok("유저 운동 스탯 업데이트 완료");
    }

    @GetMapping("/analysis/report")
    @Operation(summary = "운동 분석 리포트 결과", description = "DB의 유저 프로필 정보들을 통해 운동 분석 결과를 조회")
    public ResponseEntity<ExerciseReportResponse> getExerciseReport(@AuthenticationPrincipal UserDetails userDetails) {

        ExerciseReportResponse exerciseReportResponse = exerciseReportService
                .getReportResponse(UUID.fromString(userDetails.getUsername()));
        
        return ResponseEntity.ok(exerciseReportResponse);
    }
    

}