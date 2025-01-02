package org.hepi.hepi_sv.web.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.hepi.hepi_sv.common.util.ClientIpExtraction;
import org.hepi.hepi_sv.nutrition.dto.MealNutrientComposition;
import org.hepi.hepi_sv.nutrition.dto.RecommendSource;
import org.hepi.hepi_sv.nutrition.enums.PAType;
import org.hepi.hepi_sv.nutrition.service.NutritionAnalysisService;
import org.hepi.hepi_sv.nutrition.service.SourceRecommendService;
import org.hepi.hepi_sv.product.dto.ShopProductDTO;
import org.hepi.hepi_sv.product.service.ProductRecommendService;
import org.hepi.hepi_sv.web.dto.nutrition.WebNutrientRequest;
import org.hepi.hepi_sv.web.dto.nutrition.WebNutrientResult;
import org.hepi.hepi_sv.web.dto.nutrition.WebRecommendFood;
import org.hepi.hepi_sv.web.entity.WebNutriAnalysisData;
import org.hepi.hepi_sv.web.entity.WebNutriInputData;
import org.hepi.hepi_sv.web.repository.WebNutriAnalysisDataRepository;
import org.hepi.hepi_sv.web.repository.WebNutriInputDataRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WebNutritionService {

    private final ClientIpExtraction clientIpExtraction;
    private final NutritionAnalysisService nutrientAnalysisService;
    private final SourceRecommendService sourceRecommendService;
    private final ProductRecommendService productRecommendService;
    private final WebNutriInputDataRepository webNutriInputDataRepository;
    private final WebNutriAnalysisDataRepository webNutriAnalysisDataRepository;

    @Transactional
    private void recordUserNutriData(WebNutrientRequest request, WebNutrientResult result,
            HttpServletRequest servletRequest) {

        String clientIp = clientIpExtraction.getClientIp(servletRequest);
        String userAgent = servletRequest.getHeader("User-Agent");

        WebNutriInputData inputData = WebNutriInputData.builder()
                .gender(request.getSex())
                .age(request.getAge())
                .height(request.getHeight())
                .weight(request.getWeight())
                .sleep(request.getSleep())
                .wakeup(request.getWakeup())
                .pa(request.getPA())
                .dietGoal(request.getDietGoal())
                .dietType(request.getDietType())
                .clientIp(clientIp)
                .userAgent(userAgent)
                .recordAt(LocalDateTime.now())
                .build();

        WebNutriInputData savedinputData = webNutriInputDataRepository.save(inputData);

        WebNutriAnalysisData analysisData = WebNutriAnalysisData.builder()
                .id(savedinputData.getId())
                .bmi(result.getBMI())
                .bmr(result.getBMR())
                .tdee(result.getTDEE())
                .targetCalory(result.getTargetCalory())
                .build();

        webNutriAnalysisDataRepository.save(analysisData);
    }
    
    // TDEE 계산
    public double getTDEE(double BMR, String PA){
        // String -> pa_type 변환
        PAType pa = PAType.fromDescription(PA);

        return nutrientAnalysisService.calculateTDEE(BMR, pa);
    } 

    // 식단 영양 성분 가져오기
    public MealNutrientComposition getMealNutrientComposition(Double targetCalory, String dietType) {
        
        int dietTypeId = 1;

        switch (dietType) {
            case "일반적" -> dietTypeId = 1;
            case "저탄수화물" -> dietTypeId = 2;
            case "고탄수화물" -> dietTypeId = 3;
            case "저지방" -> dietTypeId = 4;
            case "비건" -> dietTypeId = 5;
            default -> dietTypeId = 1;
        }

        MealNutrientComposition mealNutrientComposition = nutrientAnalysisService.getMealNutrientComposition(targetCalory, dietTypeId);

        return mealNutrientComposition;
    }

    // 추천 급원 DB에서 가져오기
    public RecommendSource getRecommendSource(String dietType) {

        int dietTypeId = dietType.equals("비건") ? 5 : 1;

        List<String> carbohydrate = sourceRecommendService.getRecommendSource(1, dietTypeId);
        List<String> protein = sourceRecommendService.getRecommendSource(2, dietTypeId);
        List<String> fat = sourceRecommendService.getRecommendSource(3, dietTypeId);

        return new RecommendSource(carbohydrate, protein, fat);
    }

    // 3대영양소에 따른 음식 추천
    private WebRecommendFood getRecommendFoodByMajorNutrient(String dietType) {
        WebRecommendFood products = new WebRecommendFood();

        int dietTypeId = dietType.equals("비건") ? 5 : 1;

        List<ShopProductDTO> carbohydrate = productRecommendService.getRecommendFoodByMajorNutrient(1, dietTypeId);
        List<ShopProductDTO> protein = productRecommendService.getRecommendFoodByMajorNutrient(2, dietTypeId);
        List<ShopProductDTO> fat = productRecommendService.getRecommendFoodByMajorNutrient(3, dietTypeId);

        Collections.shuffle(carbohydrate);
        Collections.shuffle(protein);
        Collections.shuffle(fat);

        products.setCarbohydrate(carbohydrate);
        products.setProtein(protein);
        products.setFat(fat);

        return products;
    }

    public WebNutrientResult getNutritionAnalysis(WebNutrientRequest request, HttpServletRequest servletRequest) {
        double BMI = Math.round(nutrientAnalysisService.calculateBMI(request.getHeight(), request.getWeight()) * 100)
                / 100.0;
        double BMR = nutrientAnalysisService.calculateBMR(request.getSex(), request.getHeight(), request.getWeight(),
                request.getAge());
        double TDEE = getTDEE(BMR, request.getPA());
        double targetCalory = nutrientAnalysisService.calculateTargetCalory(TDEE, request.getDietGoal());
        MealNutrientComposition composition = getMealNutrientComposition(targetCalory, request.getDietType());
        List<String> mealTimes = nutrientAnalysisService.recommendMealTimes(request.getWakeup(), request.getSleep());
        RecommendSource recommendSource = getRecommendSource(request.getDietType());

        WebRecommendFood recommendProduct = getRecommendFoodByMajorNutrient(request.getDietType());

        WebNutrientResult result = new WebNutrientResult();
        result.setBMI(BMI);
        result.setBMR(Math.floor(BMR));
        result.setTDEE(Math.floor(TDEE));
        result.setTargetCalory(Math.floor(targetCalory));
        result.setDietGoal(request.getDietGoal());
        result.setComposition(composition);
        result.setWakeup(request.getWakeup());
        result.setSleep(request.getSleep());
        result.setMealTimes(mealTimes);
        result.setSources(recommendSource);
        result.setProducts(recommendProduct);

        // 데이터베이스에 기록
        recordUserNutriData(request, result, servletRequest);

        return result;
    }
    
}
