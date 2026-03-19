package com.studentdashboard.controller;

import com.studentdashboard.service.SummarizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/summarizer")
public class SummarizerController {

    @Autowired
    private SummarizerService summarizerService;

    @PostMapping("/upload-pdf")
    public ResponseEntity<Map<String, String>> summarizePdf(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();
        try {
            String summary = summarizerService.summarizePdf(file);
            response.put("summary", summary);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            response.put("error", "Error processing PDF: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/text")
    public ResponseEntity<Map<String, String>> summarizeText(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        String text = request.get("text");

        if (text == null || text.trim().isEmpty()) {
            response.put("error", "No text provided.");
            return ResponseEntity.badRequest().body(response);
        }

        String summary = summarizerService.summarizeText(text);
        response.put("summary", summary);
        return ResponseEntity.ok(response);
    }
}
