package org.bodyguide_sv.recommend.service;

import java.util.List;

import org.bodyguide_sv.recommend.entity.RecommendSource;
import org.bodyguide_sv.recommend.repository.RecommendSourceRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecommendSourceService {

    private final RecommendSourceRepository recommendSourceRepository;

    // 추천 급원 DB에서 가져오기
    public List<String> getRecommendSource(int nutrientTypeId, int dietTypeId) {

        List<RecommendSource> sources = recommendSourceRepository.findByNutrientTypeIdAndDietTypeId(nutrientTypeId,
                dietTypeId);

        List<String> sourceStrings = sources.stream()
                .map(RecommendSource::getSource)
                .toList();

        return sourceStrings;
    }

}
