package com.project.NeuroCards.service.impl;

import com.project.NeuroCards.config.DotenvConfig;
import com.project.NeuroCards.service.AiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class AiServiceImpl implements AiService {

    private final WebClient webClient;
    private final String apiKey;

    public AiServiceImpl() {

        this.apiKey = DotenvConfig.dotenv.get("GROQ_API_KEY");

        this.webClient = WebClient.builder()
                .baseUrl("https://api.groq.com/openai/v1/chat/completions")
                .build();
    }

    @Override
    public List<String[]> generateFlashcards(String text) {

        String limitedText = text.substring(0, Math.min(text.length(), 2000));

        String prompt = "Generate exactly 5 flashcards.\nFormat:\nQ: question\nA: answer\n\nContent:\n" + limitedText;

        // REQUEST BODY
        Map<String, Object> body = new HashMap<>();
        body.put("model", "llama-3.1-8b-instant");

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        messages.add(userMessage);

        body.put("messages", messages);
        body.put("temperature", 0.7);
        body.put("max_tokens", 300);
        body.put("stream", false);

        // API CALL
        Map response = webClient.post()
                .uri("")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String content = "";

        try {
            List choices = (List) response.get("choices");

            Map firstChoice = (Map) choices.get(0);

            Map messageMap = (Map) firstChoice.get("message");

            content = (String) messageMap.get("content");

        } catch (Exception e) {
            throw new RuntimeException("AI parsing failed", e);
        }

        return parse(content);
    }

    // PARSE RESPONSE
    private List<String[]> parse(String text) {

        List<String[]> list = new ArrayList<>();
        String[] lines = text.split("\\n");

        String q = null;

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("Q:")) {
                q = line.substring(2).trim();
            }
            else if (line.startsWith("A:") && q != null) {
                list.add(new String[]{q, line.substring(2).trim()});
                q = null;
            }
        }

        if (list.isEmpty()) {
            list.add(new String[]{
                    "Explain the content",
                    text.length() > 150 ? text.substring(0, 150) : text
            });
        }

        return list;
    }
}