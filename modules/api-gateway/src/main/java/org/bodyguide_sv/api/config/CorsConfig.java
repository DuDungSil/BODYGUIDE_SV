// package org.bodyguide_sv.api.config;

// import java.util.Arrays;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.reactive.CorsWebFilter;
// import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;


// @Configuration
// public class CorsConfig {
    
//     // 잘모름

//     // @Bean
//     // public CorsWebFilter corsWebFilter() {
//     //     CorsConfiguration config = new CorsConfiguration();
//     //     config.setAllowCredentials(true); // 인증 정보 포함 허용 (JWT 인증 필요 시)
//     //     config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "https://your-frontend.com")); // 허용할 도메인
//     //     config.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
//     //     config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드

//     //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//     //     source.registerCorsConfiguration("/**", config); // 모든 경로에 CORS 적용

//     //     return new CorsWebFilter(source);
//     // }

// }
