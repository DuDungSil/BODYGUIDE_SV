package org.hepi.hepi_sv.web.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hepi.hepi_sv.common.util.ClientIpExtraction;
import org.hepi.hepi_sv.exercise.dto.ExerciseAbility;
import org.hepi.hepi_sv.exercise.dto.ExerciseAnalysisProfile;
import org.hepi.hepi_sv.exercise.dto.MuscleProfile;
import org.hepi.hepi_sv.exercise.enums.MuscleGroupType;

import static org.hepi.hepi_sv.exercise.enums.MuscleGroupType.ARM;
import static org.hepi.hepi_sv.exercise.enums.MuscleGroupType.CORE;
import org.hepi.hepi_sv.exercise.service.ExerciseAnalysisService;
import org.hepi.hepi_sv.exercise.service.ExerciseMetaService;
import org.hepi.hepi_sv.nutrition.dto.NutrientProfile;
import org.hepi.hepi_sv.nutrition.service.NutrientRecommendService;
import org.hepi.hepi_sv.coupang.dto.CoupangProductDTO;
import org.hepi.hepi_sv.coupang.service.CoupangProductRecommendService;
import org.hepi.hepi_sv.web.dto.exercise.WebExerciseRequest;
import org.hepi.hepi_sv.web.dto.exercise.WebExerciseResult;
import org.hepi.hepi_sv.web.dto.exercise.WebPurposeRecommend;
import org.hepi.hepi_sv.web.entity.WebExerAnalysisData;
import org.hepi.hepi_sv.web.entity.WebExerInputData;
import org.hepi.hepi_sv.web.repository.WebExerAnalysisDataRepository;
import org.hepi.hepi_sv.web.repository.WebExerInputDataRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WebExerciseService {

    private final ClientIpExtraction clientIpExtraction;
    private final ExerciseAnalysisService exerciseAnalysisService;
    private final ExerciseMetaService exerciseMetaService;
    private final NutrientRecommendService nutrientRecommendService;
    private final CoupangProductRecommendService coupangProductRecommendService;
    private final WebExerInputDataRepository webExerInputDataRepository;
    private final WebExerAnalysisDataRepository webExerAnalysisDataRepository;

    @Transactional
    private void recordUserExerData(WebExerciseRequest request, WebExerciseResult result,
            HttpServletRequest servletRequest) {

        String clientIp = clientIpExtraction.getClientIp(servletRequest);
        String userAgent = servletRequest.getHeader("User-Agent");

        WebExerInputData inputData = WebExerInputData.builder()
                .gender(request.getSex())
                .age(request.getAge())
                .height(request.getHeight())
                .weight(request.getWeight())
                .benchWeight(request.getBench().getWeight())
                .benchReps(request.getBench().getReps())
                .squatWeight(request.getSquat().getWeight())
                .squatReps(request.getSquat().getReps())
                .deadWeight(request.getDead().getWeight())
                .deadReps(request.getDead().getReps())
                .overheadWeight(request.getOverhead().getWeight())
                .overheadReps(request.getOverhead().getReps())
                .pushupReps(request.getPushup().getReps())
                .pullupReps(request.getPullup().getReps())
                .supplePurpose(request.getSupplePurpose())
                .clientIp(clientIp)
                .userAgent(userAgent)
                .recordAt(LocalDateTime.now())
                .build();

        WebExerInputData savedinputData = webExerInputDataRepository.save(inputData);

        WebExerAnalysisData analysisData = WebExerAnalysisData.builder()
                .id(savedinputData.getId())
                .totalScore(result.getTotalScore())
                .bigThree(result.getBigThree())
                .benchScore(result.getAbility().getBench().getScore())
                .bench1RM(result.getAbility().getBench().getStrength())
                .squatScore(result.getAbility().getSquat().getScore())
                .squat1RM(result.getAbility().getSquat().getStrength())
                .deadScore(result.getAbility().getDead().getScore())
                .dead1RM(result.getAbility().getDead().getStrength())
                .overheadScore(result.getAbility().getOverhead().getScore())
                .overhead1RM(result.getAbility().getOverhead().getStrength())
                .pushupScore(result.getAbility().getPushup().getScore())
                .pullupScore(result.getAbility().getPullup().getScore())
                .build();

        webExerAnalysisDataRepository.save(analysisData);
    }

    // 상대적으로 낮은 부위
    public List<MuscleProfile> getWeekBodyPartList(ExerciseAbility ability) {
        // 1. 모든 운동 프로필 수집
        List<ExerciseAnalysisProfile> profiles = Arrays.asList(
            ability.getBench(),
            ability.getSquat(),
            ability.getDead(),
            ability.getOverhead(),
            ability.getPushup(),
            ability.getPullup()
        );
    
        // 2. 점수 기준 정렬
        profiles.sort(Comparator.comparingDouble(ExerciseAnalysisProfile::getScore));
    
        // 3. 상위 두 가지와 점수 40 미만 부위 식별 (중복 제거)
        Set<MuscleGroupType> targetMuscleGroups = new LinkedHashSet<>();
        for (int i = 0; i < Math.min(2, profiles.size()); i++) {
            targetMuscleGroups.add(profiles.get(i).getMuscleGroupType());
        }
        for (ExerciseAnalysisProfile profile : profiles) {
            if (profile.getScore() < 40) {
                targetMuscleGroups.add(profile.getMuscleGroupType());
            }
        }
    
        // 4. 식별된 근육 그룹에 대해 쿼리 수행 (기존 쿼리 활용)
        List<MuscleProfile> result = new ArrayList<>();
        for (MuscleGroupType muscleGroup : targetMuscleGroups) {
            // 4-1. Strength 가져오기
            String strength = exerciseMetaService.getStrengthByMuscleGroup(muscleGroup);
    
            // 4-2. 세부 근육 부위 가져오기
            List<String> details = exerciseMetaService.getDetailMuscleByMuscleGroup(muscleGroup);
    
            // 4-3. DTO 생성 및 결과에 추가
            MuscleProfile dto = new MuscleProfile();
            dto.setStrength(strength);
            dto.setDetails(details);
            result.add(dto);
        }
    
        return result;
    }

    // 운동 목적에 따른 추천
    public List<WebPurposeRecommend> getRecommendNutirientForPurpose(String[] purposes) {

        List<WebPurposeRecommend> list = new ArrayList<>();

        for (String purpose : purposes) {

            WebPurposeRecommend recommend = new WebPurposeRecommend();

            // db 쿼리 키 : purpose
            List<NutrientProfile> profiles = nutrientRecommendService.getRecommendNutirientForPurpose(purpose);

            recommend.setPurpose(purpose);
            recommend.setProfiles(profiles);

            list.add(recommend);
        }

        return list;
    }

    // 운동 수준에 따른 상품 추천
    private List<CoupangProductDTO> getRecommendSupplementByNutrientProfiles(List<NutrientProfile> profiles) {

        List<CoupangProductDTO> list = new ArrayList<>();

        List<Integer> nutritientId_list = new ArrayList<>();
        for (NutrientProfile profile : profiles) {
            nutritientId_list.add(profile.getId());
        }

        nutritientId_list.stream().distinct().collect(Collectors.toList());

        for (int nutritionId : nutritientId_list) {

            List<CoupangProductDTO> shopProducts = coupangProductRecommendService.getRecommendSupplementByNutrition(nutritionId);

            for (CoupangProductDTO shopProduct : shopProducts) {
                list.add(shopProduct);
            }
        }

        Collections.shuffle(list);

        return list;
    }

    // 운동 목적에 따른 상품 추천
    private List<CoupangProductDTO> getRecommendSupplementByPurposeRecommends(List<WebPurposeRecommend> recommends) {
        
            List<CoupangProductDTO> list = new ArrayList<>();

            List<Integer> nutrient_list = new ArrayList<>();
            for (WebPurposeRecommend recommend : recommends) {
                for (NutrientProfile profile : recommend.getProfiles()) {
                    nutrient_list.add(profile.getId());
                }
            }
    
            nutrient_list.stream().distinct().collect(Collectors.toList());
    
            for (int nutrient : nutrient_list) {
                List<CoupangProductDTO> shopProducts = coupangProductRecommendService.getRecommendSupplementByNutrition(nutrient);
                for (CoupangProductDTO shopProduct : shopProducts) {
                    list.add(shopProduct);
                }
            }
    
            Collections.shuffle(list);

            return list;
    }

    public WebExerciseResult getExerciseAnalysis(WebExerciseRequest request, HttpServletRequest servletRequest) {
        ExerciseAbility ability = new ExerciseAbility();
        ability.setBench(exerciseAnalysisService.analyzeExercise(120, request.getSex(), request.getWeight(),
                request.getBench().getWeight(), request.getBench().getReps()));
        ability.setSquat(exerciseAnalysisService.analyzeExercise(251, request.getSex(), request.getWeight(),
                request.getSquat().getWeight(), request.getSquat().getReps()));
        ability.setDead(exerciseAnalysisService.analyzeExercise(1, request.getSex(), request.getWeight(),
                request.getDead().getWeight(), request.getDead().getReps()));
        ability.getDead().setMuscleGroupType(CORE); // 현재 전신
        ability.setOverhead(exerciseAnalysisService.analyzeExercise(150, request.getSex(), request.getWeight(),
                request.getOverhead().getWeight(), request.getOverhead().getReps()));
        ability.setPushup(exerciseAnalysisService.analyzeExercise(132, request.getSex(), request.getWeight(),
                request.getPushup().getWeight(), request.getPushup().getReps()));
        ability.getPushup().setMuscleGroupType(ARM); // 현재 가슴       
        ability.setPullup(exerciseAnalysisService.analyzeExercise(90, request.getSex(), request.getWeight(),
                request.getPullup().getWeight(), request.getPullup().getReps()));

        int totalScore = (int) ((ability.getBench().getScore() + ability.getSquat().getScore()
                + ability.getDead().getScore() + ability.getOverhead().getScore() + ability.getPushup().getScore()
                + ability.getPullup().getScore()) / 6);
        String totalLevel = exerciseAnalysisService.getLevel(totalScore);
        double topPercent = 100 - (99.0 * totalScore / 120);
        int bigThree = (int) (ability.getBench().getStrength() + ability.getSquat().getStrength()
                + ability.getDead().getStrength());

        List<MuscleProfile> muscleDTOs = getWeekBodyPartList(ability);

        List<NutrientProfile> profiles = nutrientRecommendService.getRecommendNutirientForLevel(totalScore);
        List<WebPurposeRecommend> recommends = getRecommendNutirientForPurpose(request.getSupplePurpose());
                
        List<CoupangProductDTO> levelProducts = getRecommendSupplementByNutrientProfiles(profiles);
        List<CoupangProductDTO> purposeProducts = getRecommendSupplementByPurposeRecommends(recommends);

        WebExerciseResult result = new WebExerciseResult();
        result.setAbility(ability);
        result.setTotalScore((double)totalScore);
        result.setTotalLevel(totalLevel);
        result.setTopPercent((int) topPercent);
        result.setBigThree((double)bigThree);
        result.setWeekMuscles(muscleDTOs);
        result.setLevelRecommends(profiles);
        result.setPurposeRecommends(recommends);
        result.setLevelProducts(levelProducts);
        result.setPuporseProducts(purposeProducts);

        // DB 인서트
        recordUserExerData(request, result, servletRequest);

        return result;
    }

}
