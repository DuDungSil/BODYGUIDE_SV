package org.bodyguide_sv.web.controller;

import java.util.List;

import org.bodyguide_sv.exercise.service.ExerciseMetaService;
import org.bodyguide_sv.web.dto.exercise.WebExerciseRequest;
import org.bodyguide_sv.web.dto.exercise.WebExerciseResult;
import org.bodyguide_sv.web.dto.nutrition.WebNutrientRequest;
import org.bodyguide_sv.web.dto.nutrition.WebNutrientResult;
import org.bodyguide_sv.web.service.WebExerciseService;
import org.bodyguide_sv.web.service.WebNutritionService;
import org.bodyguide_sv.web.service.WebProductClickService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/web")
@Hidden
public class WebController {

    private final WebNutritionService webNutritionService;
    private final WebExerciseService webExerciseService;
    private final ExerciseMetaService exerMetaService;
    private final WebProductClickService webProductClickService;

    @PostMapping("/nutrient")
    public ResponseEntity<WebNutrientResult> nutrient(@RequestBody WebNutrientRequest request, HttpServletRequest servletRequest) {
        WebNutrientResult result = webNutritionService.getNutritionAnalysis(request, servletRequest);
        log.debug(result.toString());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/exercise")
    public ResponseEntity<WebExerciseResult> exercise(@RequestBody WebExerciseRequest request, HttpServletRequest servletRequest) {
        WebExerciseResult result = webExerciseService.getExerciseAnalysis(request, servletRequest);
        log.debug(result.toString());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<String>> getTag() {
        List<String> tagResult = exerMetaService.getExercisePurpose();
        return ResponseEntity.ok(tagResult);
    }
    
    @PostMapping("/productClick/{id}")
    public ResponseEntity<String> insertProductClick(@PathVariable int id, HttpServletRequest servletRequest) {
        webProductClickService.recordUserProductClickData(id, servletRequest);
        return ResponseEntity.ok("성공적으로 데이터를 등록하였습니다.");
    }   

}
