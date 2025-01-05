package org.hepi.hepi_sv.activity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/activity")
@Tag(name = "Activity", description = "유저 활동 관련")
public class ActivityController {
    
    // 유저 기록 횟수 정보 가져오기

}
