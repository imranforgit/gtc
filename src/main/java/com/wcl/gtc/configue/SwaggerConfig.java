package com.wcl.gtc.configue;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class SwaggerConfig {
 @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("gtc-api")
                .packagesToScan("com.wcl.gtc.controller", "com.wcl.gtc.dto") // Only your packages
                .build();
    }
}
