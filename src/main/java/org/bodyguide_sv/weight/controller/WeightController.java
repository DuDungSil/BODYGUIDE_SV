package org.bodyguide_sv.weight.controller;

import java.util.UUID;

import org.bodyguide_sv.weight.controller.request.WeightRecordRequest;
import org.bodyguide_sv.weight.controller.response.WeightRecordSliceResponse;
import org.bodyguide_sv.weight.service.UserWeightRecordService;
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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/weight")
@Tag(name = "Weight", description = "체중 관련")
public class WeightController {
    
    private final UserWeightRecordService userWeightRecordService;

    @GetMapping("/record")
    @Operation(summary = "체중 기록 조회", description = "체중 기록 조회 하기")
    public ResponseEntity<WeightRecordSliceResponse> getWeightRecord(@AuthenticationPrincipal UserDetails userDetails, @RequestParam int page, @RequestParam int size) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        WeightRecordSliceResponse response = userWeightRecordService.fetchWeightRecord(userId, page, size);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/record")
    @Operation(summary = "체중 기록 저장", description = "체중 기록 저장 하기")
    public ResponseEntity<String> recordWeightRecord(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody WeightRecordRequest request) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        userWeightRecordService.saveWeightRecord(userId, request);

        return ResponseEntity.ok("체중 기록 저장 완료");
    }

    @DeleteMapping("/record")
    @Operation(summary = "체중 기록 삭제", description = "체중 기록 삭제 하기")
    public ResponseEntity<String> deleteWeightRecord(@AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long historyId) {
        UUID userId = UUID.fromString(userDetails.getUsername());
        userWeightRecordService.deleteWeightRecord(userId, historyId);

        return ResponseEntity.ok("체중 기록 삭제 완료");
    }

}
