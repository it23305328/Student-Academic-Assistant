package com.studentdashboard.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

@Service
public class SummarizerService {

    private static final Logger logger = LoggerFactory.getLogger(SummarizerService.class);

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent";

    public String summarizePdf(MultipartFile file) throws IOException {
        String textContent = extractTextFromPdf(file);
        return summarizeText(textContent);
    }

    public String summarizeText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "No content provided to summarize.";
        }

        // Limit text length to handle token limits
        String truncatedText = text.length() > 10000 ? text.substring(0, 10000) : text;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String urlWithKey = GEMINI_URL + "?key=" + apiKey;

        // Exact match for the JSON structure: {"contents":[{"parts":[{"text":"..."}]}]}
        Map<String, Object> requestBody = new HashMap<>();
        List<Map<String, Object>> contents = new ArrayList<>();
        Map<String, Object> content = new HashMap<>();
        List<Map<String, Object>> parts = new ArrayList<>();
        Map<String, Object> textPart = new HashMap<>();

        textPart.put("text", "Summarize the following notes into clear bullet points:\n\n" + truncatedText);
        parts.add(textPart);
        content.put("parts", parts);
        contents.add(content);
        requestBody.put("contents", contents);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(urlWithKey, entity, Map.class);

            if (response != null && response.containsKey("candidates")) {
                List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    Map<String, Object> firstCandidate = candidates.get(0);
                    Map<String, Object> contentMap = (Map<String, Object>) firstCandidate.get("content");
                    if (contentMap != null && contentMap.containsKey("parts")) {
                        List<Map<String, Object>> resParts = (List<Map<String, Object>>) contentMap.get("parts");
                        if (resParts != null && !resParts.isEmpty()) {
                            return (String) resParts.get(0).get("text");
                        }
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Gemini API Error: " + e.getResponseBodyAsString());
            return "AI Error: " + e.getRawStatusCode() + " - " + e.getStatusText();
        } catch (Exception e) {
            System.err.println("Error summarizing: " + e.getMessage());
            return "Summarization failed: " + e.getMessage();
        }

        return "Could not generate summary.";
    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
                PDDocument document = Loader.loadPDF(inputStream.readAllBytes())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}
