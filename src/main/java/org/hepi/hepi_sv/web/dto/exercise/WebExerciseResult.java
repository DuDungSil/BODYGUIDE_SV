package org.hepi.hepi_sv.web.dto.exercise;

import java.util.List;

import org.hepi.hepi_sv.exercise.dto.ExerciseAbility;
import org.hepi.hepi_sv.exercise.dto.MuscleProfile;
import org.hepi.hepi_sv.nutrition.dto.NutrientProfile;
import org.hepi.hepi_sv.coupang.dto.CoupangProductDTO;

import lombok.Data;

@Data
public class WebExerciseResult {
    Double totalScore;
    String totalLevel;
    int topPercent;
    Double bigThree;
    ExerciseAbility ability;
    List<MuscleProfile> weekMuscles;               // 약한 부위
    List<NutrientProfile> levelRecommends;        // 운동 수준에 따른 추천
    List<WebPurposeRecommend> purposeRecommends; // 운동 목적에 따른 추천
    List<CoupangProductDTO> levelProducts;
    List<CoupangProductDTO> puporseProducts;
}
