package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

        @Bean
        public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
                return builder.routes()
                                .route("prediction_service", r -> r.path("/predict/**")
                                                .filters(f -> f.addRequestHeader("X-Request-Source", "API-Gateway"))
                                                .uri("http://localhost:8080"))
                                .route("ml_service", r -> r.path("/ml/**")
                                                .filters(f -> f.rewritePath("/ml/(?<segment>.*)", "/${segment}"))
                                                .uri("http://localhost:5000"))
                                .build();
        }
}
