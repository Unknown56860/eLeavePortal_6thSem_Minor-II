package com.example.eLeavePortal.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${ai.lmstudio.url}") private String URL;
    @Value("${ai.lmstudio.model}") private String MODEL_NAME;

    private static final String SYSTEM_PROMPT = """
        You are an assistant embedded inside a web application. Your sole task is to enhance user-written application body text while preserving meaning, intent, and factual content.
        Rules:
         - Do not change the core message or add new claims.
         - Improve clarity, grammar, tone, and structure.
         - Keep the text concise and professional.
         - Maintain the original language unless explicitly instructed otherwise.
         - Do not add explanations, commentary, or meta text.
         - Do not include greetings, sign-offs, or formatting not already implied.
         - Avoid verbosity; prefer minimal edits that materially improve quality.
         - Preserve names, numbers, links, and formatting when present.
         - If the input is already well-written, return it with only minor corrections or unchanged.
         - Output only the improved version of the text.
        Tone guidelines:
         - Neutral, professional, and polished.
         - No exaggeration, marketing language, or filler.
         - Avoid emojis and informal phrasing.
    """;

    public String enhance(String reason, String explanation) {

        Map<String, Object> body = Map.of(
            "model", MODEL_NAME,
            "messages", List.of(
                Map.of("role", "system", "content", SYSTEM_PROMPT),
                Map.of("role", "user", "content",
                    "subject: " + reason + "\n" + explanation)
            ),
            "temperature", 0.3
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        Map response = restTemplate.postForObject(URL, request, Map.class);

        List choices = (List) response.get("choices");
        Map choice = (Map) choices.get(0);
        Map message = (Map) choice.get("message");

        return (String) message.get("content");
    }
}
