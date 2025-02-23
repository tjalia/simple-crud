package com.tjalia.userprofile.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("*"));  // Allow all origins
        config.setAllowedMethods(List.of("POST", "PUT", "GET", "OPTIONS", "DELETE"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type", "x-app-id",
                "x-client-id", "x-client-secret", "x-reference-id", "X-Rate-Limit-Remaining"));
        config.setExposedHeaders(List.of("Content-Disposition"));
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter();
    }

}
