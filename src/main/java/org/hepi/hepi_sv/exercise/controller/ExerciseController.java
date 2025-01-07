package org.hepi.hepi_sv.exercise.controller;

import java.util.UUID;

import org.hepi.hepi_sv.exercise.dto.ExerciseReportResponse;
import org.hepi.hepi_sv.exercise.dto.UserExerciseStatsRequest;
import org.hepi.hepi_sv.exercise.dto.UserExerciseStatsResponse;
import org.hepi.hepi_sv.exercise.service.ExerciseReportService;
import org.hepi.hepi_sv.exercise.service.UserExerciseStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exercise")
@Tag(name = "Exercise", description = "운동 관련")
public class ExerciseController {
    
    private final UserExerciseStatsService userExerciseStatsService;
    private final ExerciseReportService exerciseReportService;

    // 운동 기록 post

    // 운동 기록 수정

    // 운동 기록 삭제

    // 운동 기록 조회 여러개

    // 유저 운동 통합 기록 조회

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