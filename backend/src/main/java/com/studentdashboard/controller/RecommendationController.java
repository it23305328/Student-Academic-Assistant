package com.studentdashboard.controller;

import com.studentdashboard.dto.RecommendationsResponseDTO;
import com.studentdashboard.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{studentId}")
    public ResponseEntity<RecommendationsResponseDTO> getRecommendations(@PathVariable Long studentId) {
        RecommendationsResponseDTO recommendations = recommendationService.getRecommendationsForStudent(studentId);
        return ResponseEntity.ok(recommendations);
    }
}
