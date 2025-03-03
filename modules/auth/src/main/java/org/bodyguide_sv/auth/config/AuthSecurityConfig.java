package org.bodyguide_sv.auth.config;

import org.bodyguide_sv.auth.client.CustomAccessTokenResponseClient;
import org.bodyguide_sv.auth.handler.OAuth2FailureHandler;
import org.bodyguide_sv.auth.handler.OAuth2SuccessHandler;
import org.bodyguide_sv.auth.service.CustomOAuth2UserService;
import org.bodyguide_sv.common.filter.TokenAuthenticationFilter;
import org.bodyguide_sv.common.filter.TokenExceptionFilter;
import org.bodyguide_sv.common.handler.CustomAccessDeniedHandler;
import org.bodyguide_sv.common.handler.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class AuthSecurityConfig {

    private final CustomAccessTokenResponseClient accessTokenResponseClient;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;
    private final TokenAuthenticationFilter tokenAuthenticationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                // error endpoint를 열어줘야 함, favicon.ico 추가!
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // rest api 설정
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화 -> cookie를 사용하지 않으면 꺼도 된다. (cookie를 사용할 경우 httpOnly(XSS 방어), sameSite(CSRF 방어)로 방어해야 한다.)
                .cors(AbstractHttpConfigurer::disable) // cors 비활성화 -> 프론트와 연결 시 따로 설정 필요
                .httpBasic(AbstractHttpConfigurer::disable) // 기본 인증 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 기본 login form 비활성화
                .logout(AbstractHttpConfigurer::disable) // 기본 logout 비활성화
                .headers(c -> c.frameOptions(
                FrameOptionsConfig::disable).disable()) // X-Frame-Options 비활성화
                .sessionManagement(c
                        -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용하지 않음

                // 엔드포인트 접근 권한 설정
                .authorizeHttpRequests(request
                        -> request.requestMatchers(
                        new AntPathRequestMatcher("/"),
                        new AntPathRequestMatcher("/swagger-ui/**"),
                        new AntPathRequestMatcher("/v3/api-docs/**"),
                        new AntPathRequestMatcher("/auth/callback"),
                        new AntPathRequestMatcher("/auth/refresh"),
                        new AntPathRequestMatcher("/auth/test"),
                        new AntPathRequestMatcher("/web/**")
                ).permitAll()
                        // GUEST만 접근 가능한 엔드포인트
                        .requestMatchers(
                                new AntPathRequestMatcher("/auth/initialize")
                        ).hasRole("GUEST")
                        // 그외 엔드포인트 USER 이상만
                        .anyRequest().hasAnyRole("USER", "ADMIN")
                )
                // oauth2 설정
                .oauth2Login(oauth
                        -> // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                        // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
                        oauth.tokenEndpoint(token -> token.accessTokenResponseClient(accessTokenResponseClient))
                        .userInfoEndpoint(info -> info.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler) // 로그인 성공 시 핸들러
                        .failureHandler(oAuth2FailureHandler) // 로그인 실패 시 핸들러
                )
                // 필터 설정
                .addFilterBefore(tokenAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new TokenExceptionFilter(), tokenAuthenticationFilter.getClass())
                // 인증 예외 핸들링
                .exceptionHandling((exceptions) -> exceptions
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler()));

        return http.build();
    }
}
