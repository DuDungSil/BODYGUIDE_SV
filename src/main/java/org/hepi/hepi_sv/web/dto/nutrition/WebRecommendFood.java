package org.hepi.hepi_sv.web.dto.nutrition;

import java.util.List;

import org.hepi.hepi_sv.coupang.dto.CoupangProductDTO;

import lombok.Data;

@Data
public class WebRecommendFood {
    List<CoupangProductDTO> carbohydrate;
    List<CoupangProductDTO> protein;
    List<CoupangProductDTO> fat;
}
