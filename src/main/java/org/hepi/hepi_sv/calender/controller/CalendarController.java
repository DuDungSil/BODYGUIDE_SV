package org.hepi.hepi_sv.calender.controller;

import java.util.List;
import java.util.UUID;

import org.hepi.hepi_sv.calender.dto.CalendarMemoDTO;
import org.hepi.hepi_sv.calender.service.CalendarService;
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
@RequestMapping("/calender")
public class CalendarController {

    private final CalendarService calendarService;

    // 메모 날짜
    @GetMapping("/memo")
    public ResponseEntity<List<String>> getMemoDays(@AuthenticationPrincipal UserDetails userDetails) {
        List<String> memoDays = calendarService.getMemoDays(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(memoDays);
    }

    // 메모 날짜 상세
    @GetMapping("/memo/{number}")
    public ResponseEntity<CalendarMemoDTO> getMemoDayDetail(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("number") String number) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        CalendarMemoDTO calendarMemoDTO = calendarService.getCalendarMemoDetail(userId, number);
        return ResponseEntity.ok(calendarMemoDTO);
    }


    //  운동 기록
    @GetMapping("/exercise")
    public ResponseEntity<List<String>> getExerciseDays(@AuthenticationPrincipal UserDetails userDetails) {
        List<String> exerciseDays = calendarService.getExerciseDays(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(exerciseDays);
    }

    // 운동 날짜 상세
    @GetMapping("/exercise/{number}")
    public ResponseEntity<String> getExerciseDayDetail(@PathVariable("number") Integer number) {
        return ResponseEntity.ok("Number provided: " + number + ". Displaying specific exercise data.");
    }

    //  섭취 기록
    @GetMapping("/nutrition")
    public ResponseEntity<List<String>> getNutritionDays(@AuthenticationPrincipal UserDetails userDetails) {
        List<String> nutritionDays = calendarService.getNutritionDays(UUID.fromString(userDetails.getUsername()));
        return ResponseEntity.ok(nutritionDays);
    }

    // 섭취 날짜 상세
    @GetMapping("/nutrition/{number}")
    public ResponseEntity<String> getNutritionDayDetail(@PathVariable("number") Integer number) {
        return ResponseEntity.ok("Number provided: " + number + ". Displaying specific nutrition data.");
    }

    //  섭취 기록
    @GetMapping("/weight")
    public ResponseEntity<String> getWeightDays() {
        return ResponseEntity.ok("Number not provided. Displaying default weight data.");
    }

    // 섭취 날짜 상세
    @GetMapping("/weight/{number}")
    public ResponseEntity<String> getWeightDayDetail(@PathVariable("number") Integer number) {
        return ResponseEntity.ok("Number provided: " + number + ". Displaying specific weight data.");
    }

    // 메모 입력 & 수정 & 삭제

}
