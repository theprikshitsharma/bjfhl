package com.prikshit.bfhl.service;

import org.springframework.beans.factory.annotation.Value;
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

        RestTemplate restTemplate = new RestTemplate();

        String prompt = "Answer in one word only: " + question;

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(
                Map.of("parts", List.of(
                        Map.of("text", prompt)
                ))
        ));

        String fullUrl = url + "?key=" + apiKey;

        Map response = restTemplate.postForObject(fullUrl, body, Map.class);

        try {
            List candidates = (List) response.get("candidates");
            Map content = (Map) ((Map) candidates.get(0)).get("content");
            List parts = (List) content.get("parts");
            String text = (String) ((Map) parts.get(0)).get("text");
            return text.trim().split("\\s+")[0];
        } catch (Exception e) {
            return "Unknown";
        }
    }
}
