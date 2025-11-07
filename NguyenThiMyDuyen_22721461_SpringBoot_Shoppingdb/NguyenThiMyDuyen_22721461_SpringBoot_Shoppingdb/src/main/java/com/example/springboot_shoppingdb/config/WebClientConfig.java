package com.example.springboot_shoppingdb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // provide a sensible default so app doesn't fail when property is missing
    @Value("${gemini.base-url:https://generativelanguage.googleapis.com}")
    private String baseUrl;

    // keep apiKey available to service, but do NOT attach Authorization globally here
    @Value("${gemini.api.key:}")
    private String apiKey;

    @Bean
    public WebClient geminiWebClient(WebClient.Builder builder) {
        // Build WebClient without Authorization header so per-request auth can be chosen
        return builder
                .baseUrl(baseUrl)
                .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
