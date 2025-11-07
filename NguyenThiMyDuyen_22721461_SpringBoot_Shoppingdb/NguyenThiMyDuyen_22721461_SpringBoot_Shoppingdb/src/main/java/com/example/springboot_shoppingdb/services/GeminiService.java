package com.example.springboot_shoppingdb.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    private static final Logger log = LoggerFactory.getLogger(GeminiService.class);

    private final WebClient client;

    @Value("${gemini.api.key:}")
    private String apiKey;

    @Value("${gemini.auth.mode:auto}")
    private String authMode;

    @Value("${gemini.base-url:https://generativelanguage.googleapis.com}")
    private String baseUrl;

    @Value("${gemini.generate.path:/v1beta/models/gemini-2.5-flash:generateContent}")
    private String generatePath;

    // SỬA LỖI MAX_TOKENS: Tăng giá trị mặc định từ 256 lên 2048
    @Value("${gemini.maxOutputTokens:2048}")
    private int maxOutputTokens;

    public GeminiService(WebClient geminiWebClient) {
        this.client = geminiWebClient;
    }

    public Mono<String> sendMessage(String userMessage) {
        final Map<String, Object> body;
        if (generatePath != null && generatePath.toLowerCase().contains("generatecontent")) {
            body = Map.of(
                    "contents", List.of(
                            Map.of(
                                    "role", "user",
                                    "parts", List.of(
                                            Map.of("text", userMessage)
                                    )
                            )
                    ),
                    "generationConfig", Map.of(
                            "maxOutputTokens", maxOutputTokens
                    )
            );
        } else {
            body = Map.of(
                    "prompt", Map.of("text", userMessage),
                    "maxOutputTokens", maxOutputTokens
            );
        }

        String path = generatePath;
        String key = apiKey != null ? apiKey.trim() : "";
        String mode = authMode != null ? authMode.trim().toLowerCase() : "auto";
        final boolean useQuery;
        final boolean useBearerHeader;
        if ("query".equals(mode)) {
            useQuery = true;
            useBearerHeader = false;
        } else if ("bearer".equals(mode)) {
            useQuery = false;
            useBearerHeader = true;
        } else {
            if (!key.isBlank() && (key.startsWith("ya29.") || key.startsWith("Bearer "))) {
                useQuery = false;
                useBearerHeader = true;
            } else if (!key.isBlank()) {
                useQuery = true;
                useBearerHeader = false;
            } else {
                useQuery = false;
                useBearerHeader = false;
            }
        }
        final String finalUrl = (baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl)
                + path
                + ((useQuery && !key.isBlank()) ? "?key=" + key : "");
        final String maskedUrl = finalUrl.replaceAll("(\\?key=)(.*)$", "$1REDACTED");
        log.debug("Calling Gemini API: method=POST url={} authMode={} useQuery={} useBearerHeader={}",
                maskedUrl, mode, useQuery, useBearerHeader);
        if (log.isDebugEnabled()) {
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                log.debug("Gemini API Request Body: {}", mapper.writeValueAsString(body));
            } catch (Exception e) {
                log.debug("Gemini API Request Body (raw map): {}", body);
            }
        }

        return client.post()
                .uri(uriBuilder -> {
                    var ub = uriBuilder.path(path);
                    if (useQuery && !key.isBlank()) {
                        ub = ub.queryParam("key", key);
                    }
                    return ub.build();
                })
                .headers(headers -> {
                    if (useBearerHeader && !key.isBlank()) {
                        if (key.startsWith("Bearer ")) {
                            headers.set(HttpHeaders.AUTHORIZATION, key);
                        } else {
                            headers.setBearerAuth(key);
                        }
                    }
                })
                .bodyValue(body)
                .exchangeToMono(resp -> {
                    if (resp.statusCode().is2xxSuccessful()) {
                        return resp.bodyToMono(JsonNode.class)
                                .map(jsonNode -> {

                                    // SỬA LỖI 1: Kiểm tra safety block TRƯỚC
                                    if (jsonNode.has("promptFeedback") && jsonNode.path("promptFeedback").has("blockReason")) {
                                        String reason = jsonNode.path("promptFeedback").path("blockReason").asText();
                                        log.warn("Gemini API blocked request. Reason: {}", reason);
                                        // Trả về tin nhắn thân thiện, KHÔNG ném lỗi
                                        return "Xin lỗi, tôi không thể trả lời câu hỏi này (Lý do: " + reason + ")";
                                    }

                                    String text = jsonNode.path("candidates")
                                            .path(0)
                                            .path("content")
                                            .path("parts")
                                            .path(0)
                                            .path("text")
                                            .asText();

                                    if (text.isEmpty()) {
                                        log.warn("Không tìm thấy 'text' trong JSON response (có thể là cấu trúc lạ): {}", jsonNode.toString());
                                        // Trả về tin nhắn thân thiện, KHÔNG ném lỗi
                                        return "Xin lỗi, tôi không nhận được phản hồi hợp lệ từ AI.";
                                    }
                                    return text;
                                });
                    } else {
                        return resp.bodyToMono(String.class)
                                .defaultIfEmpty("")
                                .flatMap(b -> {
                                    String baseMsg = resp.statusCode().value() + " " + b + " (url=" + maskedUrl + ")";
                                    log.warn("Gemini API returned error: {}", baseMsg);
                                    String guidance = "Possible causes: API not enabled for your project, API key invalid or restricted, billing/quotas not enabled, or model not available to your project/region.";
                                    String fullMsg = baseMsg + " " + guidance;
                                    return Mono.error(new RuntimeException(fullMsg));
                                });
                    }
                });
    }

    public String generateText(String userMessage) {
        try {
            return sendMessage(userMessage).block();
        } catch (RuntimeException ex) {
            throw new RuntimeException("Gemini call failed: " + ex.getMessage(), ex);
        }
    }
}