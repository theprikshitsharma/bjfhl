package com.prikshit.bfhl.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class AIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.url}")
    private String url;

    public String askAI(String question) {

        if (question == null || question.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid question");
        }

        RestTemplate restTemplate = new RestTemplate();

        // Full URL with query parameter (IMPORTANT)
        String fullUrl = url + "?key=" + apiKey;

        // Prompt
        String prompt = "Answer in one word only: " + question;

        // Request body (Gemini format)
        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(
                Map.of("parts", List.of(
                        Map.of("text", prompt)
                ))
        ));

        // Headers (only content type needed)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        // Send request
        ResponseEntity<Map> responseEntity =
                restTemplate.postForEntity(fullUrl, entity, Map.class);

        // Validate response
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Gemini API error");
        }

        Map response = responseEntity.getBody();

        if (response == null || !response.containsKey("candidates")) {
            throw new RuntimeException("Invalid AI response");
        }

        List candidates = (List) response.get("candidates");
        Map content = (Map) ((Map) candidates.get(0)).get("content");
        List parts = (List) content.get("parts");
        String text = (String) ((Map) parts.get(0)).get("text");

        // Return single word
        return text.trim().split("\\s+")[0];
    }
}
