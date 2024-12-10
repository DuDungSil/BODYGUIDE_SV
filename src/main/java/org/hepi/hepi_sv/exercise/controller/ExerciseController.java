package org.hepi.hepi_sv.exercise.controller;

import java.util.UUID;

import org.hepi.hepi_sv.exercise.dto.ExerciseReportResponse;
import org.hepi.hepi_sv.exercise.service.ExerciseReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exer")
@Tag(name = "Exercise", description = "운동 관련")
public class ExerciseController {
    
    private final ExerciseReportService exerciseReportService;

    // 운동 분석 결과 가져오기
    @GetMapping("/analysis/report")
    @Operation(summary = "운동 분석 리포트 결과", description = "DB의 유저 프로필 정보들을 통해 운동 분석 결과를 조회")
    public ResponseEntity<ExerciseReportResponse> getExerciseReport(@AuthenticationPrincipal UserDetails userDetails) {

        ExerciseReportResponse exerciseReportResponse = exerciseReportService
                .getReportResponse(UUID.fromString(userDetails.getUsername()));
        
        return ResponseEntity.ok(exerciseReportResponse);
    }
    

}