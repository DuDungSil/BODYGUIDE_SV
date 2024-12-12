package org.hepi.hepi_sv.nutrition.dto;

import java.util.List;

public record RecommendSource(
    List<String> carbohydrate,
    List<String> protein,
    List<String> fat
) {

}
