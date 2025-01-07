package org.hepi.hepi_sv.calendar.controller;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hepi.hepi_sv.calendar.dto.CalendarMemoDTO;
import org.hepi.hepi_sv.calendar.service.CalendarService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar")
@Tag(name = "Calendar", description = "캘린더 관련")
public class CalendarController {

    private final CalendarService calendarService;

    // 메모 날짜
    @GetMapping("/memoDays/{yyyymm}")
    @Operation(summary = "메모 날짜 조회", description = "메모가 적혀있는 날짜들을 배열로 반환")
    public ResponseEntity<List<String>> getMemoDays(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String yyyymm) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        List<String> memoDays = calendarService.getMemoDays(userId, yyyymm);
        return ResponseEntity.ok(memoDays);
    }

    //  운동 기록
    @GetMapping("/exerciseDays/{yyyymm}")
    @Operation(summary = "운동 기록 날짜 조회", description = "운동 기록이 적혀있는 날짜들을 배열로 반환")
    public ResponseEntity<List<String>> getExerciseDays(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String yyyymm) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        List<String> exerciseDays = calendarService.getExerciseDays(userId, yyyymm);
        return ResponseEntity.ok(exerciseDays);
    }

    //  섭취 기록
    @GetMapping("/nutritionDays/{yyyymm}")
    @Operation(summary = "섭취 기록 날짜 조회", description = "섭취 기록이 적혀있는 날짜들을 배열로 반환")
    public ResponseEntity<List<String>> getNutritionDays(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String yyyymm) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        List<String> nutritionDays = calendarService.getNutritionDays(userId, yyyymm);
        return ResponseEntity.ok(nutritionDays);
    }

    //  체중 기록
    @GetMapping("/weightDays/{yyyymm}")
    @Operation(summary = "체중 기록 날짜 조회", description = "체중 기록이 적혀있는 날짜들을 배열로 반환")
    public ResponseEntity<List<String>> getWeightDays(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String yyyymm) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        List<String> weightDays = calendarService.getWeightDays(userId, yyyymm);
        return ResponseEntity.ok(weightDays);
    }

    // 해당 날짜 기록 상세
    @GetMapping("/detail/{yyyymmdd}")
    @Operation(summary = "특정 날짜 기록 종합 조회", description = "선택한 특정 날짜의 메모, 운동, 섭취, 체중 기록들을 일괄 조회하여 반환")
    public ResponseEntity<CalendarMemoDTO> getMemoDayDetail(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String yyyymmdd) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        CalendarMemoDTO calendarMemoDTO = calendarService.getCalendarMemoDetail(userId, yyyymmdd);
        return ResponseEntity.ok(calendarMemoDTO);
    }

    // 메모 입력 & 수정 & 삭제

}
