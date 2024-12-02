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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/exer")
public class ExerciseController {
    
    private final ExerciseReportService exerciseReportService;

    // 운동 분석 결과 가져오기
    @GetMapping("/analysis/report")
    public ResponseEntity<ExerciseReportResponse> getExerciseReport(@AuthenticationPrincipal UserDetails userDetails) {

        ExerciseReportResponse exerciseReportResponse = exerciseReportService
                .getReportResponse(UUID.fromString(userDetails.getUsername()));
        
        return ResponseEntity.ok(exerciseReportResponse);
    }
    

}