// package org.bodyguide_sv.api.filter;

// import org.bodyguide_sv.common.util.JwtUtil;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;

// import lombok.RequiredArgsConstructor;
// import reactor.core.publisher.Mono;

// @Component
// @RequiredArgsConstructor
// public class JwtAuthenticationFilter implements GlobalFilter {
    
//     private final JwtUtil jwtUtil;

//     // jwt 검증 필터
//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//         ServerHttpRequest request = exchange.getRequest();

//         if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//             return unauthorizedResponse(exchange);
//         }

//         String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION).substring(7);
//         if (!jwtUtil.validateToken(token)) {
//             return unauthorizedResponse(exchange);
//         }

//         String userId = jwtUtil.extractUserId(token);
//         String userRole = jwtUtil.exreactUserRole(token);
//         ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
//             .header("X-User-Id", userId)
//             .header("X-User-Role", userRole)
//             .build();

//         return chain.filter(exchange.mutate().request(modifiedRequest).build());
//     }

//     private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
//         exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//         return exchange.getResponse().setComplete();
//     }

// }
