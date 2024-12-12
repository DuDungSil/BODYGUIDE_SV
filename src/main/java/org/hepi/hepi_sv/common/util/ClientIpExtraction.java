package org.hepi.hepi_sv.common.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class ClientIpExtraction {
    
    public String getClientIp(HttpServletRequest request) {
        String clientIp = null;
    
        // 가장 흔히 사용되는 헤더들을 순차적으로 검사
        String[] headers = {
            "X-Forwarded-For"
        };
    
        for (String header : headers) {
            clientIp = request.getHeader(header);
            if (clientIp != null && !clientIp.isEmpty() && !"unknown".equalsIgnoreCase(clientIp)) {
                // 여러 개의 IP가 포함된 경우, 첫 번째 IP가 클라이언트 IP임
                clientIp = clientIp.split(",")[0].trim();
                return clientIp;
            }
        }
    
        // 헤더에서 찾지 못했을 때 fallback
        if (clientIp == null || clientIp.isEmpty() || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = request.getRemoteAddr();
        }
    
        return clientIp;
    }

}
