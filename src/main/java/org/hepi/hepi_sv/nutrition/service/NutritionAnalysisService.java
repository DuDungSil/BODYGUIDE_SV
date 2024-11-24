package org.hepi.hepi_sv.nutrition.service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hepi.hepi_sv.nutrition.dto.MealNutrientComposition;
import org.hepi.hepi_sv.nutrition.dto.MealMacroDetails;
import org.hepi.hepi_sv.nutrition.entity.DietType;
import org.hepi.hepi_sv.nutrition.repository.DietTypeRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NutritionAnalysisService {

    private final DietTypeRepository dietTypeRepository;

    public double calculateBMI(double height, double weight) {
        
        double m_height = height / 100;

        double BMI = weight / Math.pow(m_height, 2);
        return BMI;
    }

    public double calculateBMR(String sex, double height, double weight, int age){
        double BMR;
        if(sex.equals("M")){
            BMR = 66.5 + (13.75 * weight) + (5.003 * height) - (6.75 * age);
        }
        else{
            BMR = 655.1 + (9.563 * weight) + (1.850 * height) - (4.676 * age);
        }
        return BMR;
    } 

    public double calculateTDEE(double BMR, String PA){
        // 비활동적 / 저활동적 / 활동적 / 고활동적 / 매우활동적 
        double TDEE;
        TDEE = switch (PA) {
            case "비활동적" -> BMR * 1.2;
            case "저활동적" -> BMR * 1.375;
            case "활동적" -> BMR * 1.55;
            case "고활동적" -> BMR * 1.725;
            case "매우활동적" -> BMR * 1.9;
            default -> BMR * 1.55;
        };
        return TDEE;
    } 

    // 목적에 따른 섭취량 계산
    public double calculateTargetCalory(double TDEE, String dietGoal){
        // 체중 감소 / 체중 유지 / 벌크업
        double dietGoal_calory;
        dietGoal_calory = switch (dietGoal) {
            case "체중 감소" -> TDEE - 500;
            case "체중 유지" -> TDEE;
            case "체중 증가" -> TDEE * 1.1;
            default -> TDEE;
        };
        return dietGoal_calory;
    } 

    // 목표 칼로리와 식단 유형에 따라 탄수화물, 단백질, 불포화지방, 포화지방 함량 계산
    public MealNutrientComposition getMealNutrientComposition(double targetCalory, int dietType) {
        MealMacroDetails carbohydrate = new MealMacroDetails();
        MealMacroDetails protein = new MealMacroDetails();
        MealMacroDetails unFat = new MealMacroDetails();
        MealMacroDetails satFat = new MealMacroDetails();

        // 탄수화물, 단백질, 불포화지방, 포화지방 비율 db에서 가져오기
        DietType dietTypeEntity = dietTypeRepository.findById((long) dietType).orElseThrow(() -> new IllegalArgumentException("DietType not found for id: " + dietType));

        carbohydrate.setRatio(dietTypeEntity.getCarbohydrate());
        carbohydrate.setCalory(Math.round(targetCalory * carbohydrate.getRatio() / 100));
        carbohydrate.setGram(Math.round(carbohydrate.getCalory() * 0.25));

        protein.setRatio(dietTypeEntity.getProtein());
        protein.setCalory(Math.round(targetCalory * protein.getRatio() / 100));
        protein.setGram(Math.round(protein.getCalory() * 0.25));

        unFat.setRatio(dietTypeEntity.getUnFat());
        unFat.setCalory(Math.round(targetCalory * unFat.getRatio() / 100));
        unFat.setGram(Math.round(unFat.getCalory() / 9));

        satFat.setRatio(dietTypeEntity.getSatFat());
        satFat.setCalory(Math.round(targetCalory * satFat.getRatio() / 100));
        satFat.setGram(Math.round(satFat.getCalory() / 9));

        MealNutrientComposition composition = new MealNutrientComposition(carbohydrate, protein, unFat, satFat);

        return composition;
    }
    
    // 식사 시간 추천
    public List<String> recommendMealTimes(String wakeUpTime, String bedTime) {
        List<String> mealTimes = new ArrayList<String>();

        // 시간 형식을 맞추기 위한 포맷터
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // 기상 시간, 취침 시간 파싱
        LocalTime wakeUp = LocalTime.parse(wakeUpTime, timeFormatter);
        LocalTime bed = LocalTime.parse(bedTime, timeFormatter);

        // 아침 시간: 기상 후 1시간 후
        LocalTime breakfastTime = wakeUp.plusHours(1);

        // 저녁 시간: 취침 3시간 전
        LocalTime dinnerTime = bed.minusHours(3);

        // 아침 시간과 저녁 시간 사이의 시간 차이 계산
        Duration between = Duration.between(breakfastTime, dinnerTime);
        long totalMinutesBetween = between.toMinutes();

        // 아침 시간 추가
        mealTimes.add(breakfastTime.format(timeFormatter));

        // 아침과 저녁 사이에 추가할 식사 시간 계산
        if (totalMinutesBetween > 240) { // 4시간(240분) 이상일 때
            // 3등분으로 나누기
            long intervalMinutes = totalMinutesBetween / 3;
            
            LocalTime firstAdditionalMealTime = breakfastTime.plusMinutes(intervalMinutes);
            LocalTime secondAdditionalMealTime = breakfastTime.plusMinutes(2 * intervalMinutes);
            
            mealTimes.add(firstAdditionalMealTime.format(timeFormatter));
            mealTimes.add(secondAdditionalMealTime.format(timeFormatter));
        } else if (totalMinutesBetween > 0) {
            // 아침과 저녁 사이에 식사 1회 추가
            LocalTime additionalMealTime = breakfastTime.plusMinutes(totalMinutesBetween / 2);
            mealTimes.add(additionalMealTime.format(timeFormatter));
        }

        // 저녁 시간 추가
        mealTimes.add(dinnerTime.format(timeFormatter));

        return mealTimes;
    }
    
}

