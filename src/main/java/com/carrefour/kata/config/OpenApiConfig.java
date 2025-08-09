// src/main/java/com/carrefour/kata/config/OpenApiConfig.java
package com.carrefour.kata.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI appOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Delivery Scheduler API")
                .description("Carrefour kata — slots, reservations & AI advice.")
                .version("v0.1.0"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("delivery-scheduler")
                .packagesToScan("com.carrefour.kata")
                .pathsToMatch("/**")
                .build();
    }
}
