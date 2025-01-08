package org.bodyguide_sv.web.service;

import java.time.LocalDateTime;

import org.bodyguide_sv.common.util.ClientIpExtraction;
import org.bodyguide_sv.web.entity.WebProductClickData;
import org.bodyguide_sv.web.repository.WebProductClickDataRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WebProductClickService {

    private final ClientIpExtraction clientIpExtraction;
    private final WebProductClickDataRepository webProductClickDataRepository;

    public void recordUserProductClickData(int productId, HttpServletRequest servletRequest) {

        String clientIp = clientIpExtraction.getClientIp(servletRequest);
        String userAgent = servletRequest.getHeader("User-Agent");

        WebProductClickData clickData = WebProductClickData.builder()
                                        .productId(productId)
                                        .clientIp(clientIp)
                                        .userAgent(userAgent)
                                        .recordAt(LocalDateTime.now())
                                        .build();
                                        
        webProductClickDataRepository.save(clickData);
    }

}