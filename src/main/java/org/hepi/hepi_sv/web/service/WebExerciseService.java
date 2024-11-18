package org.hepi.hepi_sv.web.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hepi.hepi_sv.common.util.ClientIpExtraction;
import org.hepi.hepi_sv.exercise.dto.ExerciseProfile;
import org.hepi.hepi_sv.exercise.service.ExerciseAnalysisService;
import org.hepi.hepi_sv.exercise.service.ExerciseMetaService;
import org.hepi.hepi_sv.nutrition.dto.NutrientProfile;
import org.hepi.hepi_sv.nutrition.service.NutrientRecommendService;
import org.hepi.hepi_sv.product.entity.ShopProduct;
import org.hepi.hepi_sv.product.service.ProductRecommendService;
import org.hepi.hepi_sv.web.dto.exercise.BodyPart;
import org.hepi.hepi_sv.web.dto.exercise.ExerciseAbility;
import org.hepi.hepi_sv.web.dto.exercise.PurposeRecommend;
import org.hepi.hepi_sv.web.dto.exercise.WebExerciseRequest;
import org.hepi.hepi_sv.web.dto.exercise.WebExerciseResult;
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
    private final ProductRecommendService productRecommendService;
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
    public List<BodyPart> getWeekBodyPartList(ExerciseAbility ability) {
        ExerciseProfile[] profiles = new ExerciseProfile[6];

        profiles[0] = ability.getBench();
        profiles[1] = ability.getSquat();
        profiles[2] = ability.getDead();
        profiles[3] = ability.getOverhead();
        profiles[4] = ability.getPushup();
        profiles[5] = ability.getPullup();

        // 점수 기준으로 정렬하여 가장 낮은 점수 두 가지 찾기
        List<ExerciseProfile> sortedProfiles = Arrays.asList(profiles);
        sortedProfiles.sort(Comparator.comparingDouble(ExerciseProfile::getScore));

        List<BodyPart> list = new ArrayList<>();
        Set<String> addedParts = new HashSet<>();  // 중복 방지를 위한 Set

        // 가장 낮은 점수 두 가지 프로필 추가
        for (int i = 0; i < 2; i++) {
            ExerciseProfile profile = sortedProfiles.get(i);
            if (!addedParts.contains(profile.getPart())) {  // 이미 추가된 부위인지 확인
                BodyPart part = new BodyPart();

                // db 쿼리 키 : part
                String strength = exerciseMetaService.getStrengthByMuscleGroup(profile.getPart());
                List<String> details = exerciseMetaService.getDetailMuscleByMuscleGroup(profile.getPart());

                part.setStrength(strength);
                part.setDetails(details);

                list.add(part);
                addedParts.add(profile.getPart());  // 추가된 부위를 기록
            }
        }

        // 점수가 40보다 낮은 모든 부위 추가 (중복되지 않도록)
        for (ExerciseProfile profile : profiles) {
            if (profile.getScore() < 40 && !addedParts.contains(profile.getPart())) {
                BodyPart part = new BodyPart();

                // db 쿼리 키 : part
                String strength = exerciseMetaService.getStrengthByMuscleGroup(profile.getPart());
                List<String> details = exerciseMetaService.getDetailMuscleByMuscleGroup(profile.getPart());

                part.setStrength(strength);
                part.setDetails(details);

                list.add(part);
                addedParts.add(profile.getPart());  // 추가된 부위를 기록
            }
        }

        return list;
    }

    // 운동 목적에 따른 추천
    public List<PurposeRecommend> getRecommendNutirientForPurpose(String[] purposes) {

        List<PurposeRecommend> list = new ArrayList<>();

        for (String purpose : purposes) {

            PurposeRecommend recommend = new PurposeRecommend();

            // db 쿼리 키 : purpose
            List<NutrientProfile> profiles = nutrientRecommendService.getRecommendNutirientForPurpose(purpose);

            recommend.setPurpose(purpose);
            recommend.setProfiles(profiles);

            list.add(recommend);
        }

        return list;
    }

    private List<ShopProduct> getRecommendSupplementByNutrientProfiles(List<NutrientProfile> profiles) {

        List<ShopProduct> list = new ArrayList<>();

        List<String> nutrition_list = new ArrayList<>();
        for (NutrientProfile profile : profiles) {
            nutrition_list.add(profile.getName());
        }

        nutrition_list.stream().distinct().collect(Collectors.toList());

        for (String nutrition : nutrition_list) {

            List<ShopProduct> shopProducts = productRecommendService.getRecommendSupplementByNutrition(nutrition);

            for (ShopProduct shopProduct : shopProducts) {
                list.add(shopProduct);
            }
        }

        Collections.shuffle(list);

        return list;
    }

    private List<ShopProduct> getRecommendSupplementByPurposeRecommends(List<PurposeRecommend> recommends) {
        
            List<ShopProduct> list = new ArrayList<>();

            List<String> nutrition_list = new ArrayList<>();
            for (PurposeRecommend recommend : recommends) {
                for (NutrientProfile profile : recommend.getProfiles()) {
                    nutrition_list.add(profile.getName());
                }
            }
    
            nutrition_list.stream().distinct().collect(Collectors.toList());
    
            for (String nutrition : nutrition_list) {
                List<ShopProduct> shopProducts = productRecommendService.getRecommendSupplementByNutrition(nutrition);
                for (ShopProduct shopProduct : shopProducts) {
                    list.add(shopProduct);
                }
            }
    
            Collections.shuffle(list);

            return list;
    }

    public WebExerciseResult getExerciseAnalysis(WebExerciseRequest request, HttpServletRequest servletRequest) {
        ExerciseAbility ability = new ExerciseAbility();
        ability.setBench(exerciseAnalysisService.analyzeExercise("벤치 프레스", request.getSex(), request.getWeight(),
                request.getBench().getWeight(), request.getBench().getReps()));
        ability.setSquat(exerciseAnalysisService.analyzeExercise("스쿼트", request.getSex(), request.getWeight(),
                request.getSquat().getWeight(), request.getSquat().getReps()));
        ability.setDead(exerciseAnalysisService.analyzeExercise("데드리프트", request.getSex(), request.getWeight(),
                request.getDead().getWeight(), request.getDead().getReps()));
        ability.setOverhead(exerciseAnalysisService.analyzeExercise("오버헤드 프레스", request.getSex(), request.getWeight(),
                request.getOverhead().getWeight(), request.getOverhead().getReps()));
        ability.setPushup(exerciseAnalysisService.analyzeExercise("푸쉬업", request.getSex(), request.getWeight(),
                request.getPushup().getWeight(), request.getPushup().getReps()));
        ability.setPullup(exerciseAnalysisService.analyzeExercise("풀업", request.getSex(), request.getWeight(),
                request.getPullup().getWeight(), request.getPullup().getReps()));

        int totalScore = (int) ((ability.getBench().getScore() + ability.getSquat().getScore()
                + ability.getDead().getScore() + ability.getOverhead().getScore() + ability.getPushup().getScore()
                + ability.getPullup().getScore()) / 6);
        String totalLevel = exerciseAnalysisService.getLevel(totalScore);
        double topPercent = 100 - (99.0 * totalScore / 120);
        int bigThree = (int) (ability.getBench().getStrength() + ability.getSquat().getStrength()
                + ability.getDead().getStrength());

        List<BodyPart> parts = getWeekBodyPartList(ability);

        List<NutrientProfile> profiles = nutrientRecommendService.getRecommendNutirientForLevel(totalScore);
        List<PurposeRecommend> recommends = getRecommendNutirientForPurpose(request.getSupplePurpose());
                
        List<ShopProduct> levelProducts = getRecommendSupplementByNutrientProfiles(profiles);
        List<ShopProduct> purposeProducts = getRecommendSupplementByPurposeRecommends(recommends);

        WebExerciseResult result = new WebExerciseResult();
        result.setAbility(ability);
        result.setTotalScore((double)totalScore);
        result.setTotalLevel(totalLevel);
        result.setTopPercent((int) topPercent);
        result.setBigThree((double)bigThree);
        result.setParts(parts);
        result.setLevelRecommends(profiles);
        result.setPurposeRecommends(recommends);
        result.setLevelProducts(levelProducts);
        result.setPuporseProducts(purposeProducts);

        // DB 인서트
        recordUserExerData(request, result, servletRequest);

        return result;
    }

}
