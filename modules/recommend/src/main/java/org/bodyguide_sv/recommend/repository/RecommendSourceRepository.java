package org.bodyguide_sv.recommend.repository;

import java.util.List;

import org.bodyguide_sv.recommend.entity.RecommendSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendSourceRepository extends JpaRepository<RecommendSource, Integer> {

    List<RecommendSource> findByNutrientTypeIdAndDietTypeId(int nutrientTypeId, int dietTypeId);

}
