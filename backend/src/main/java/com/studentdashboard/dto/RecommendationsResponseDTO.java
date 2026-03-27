package com.studentdashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationsResponseDTO {
    private List<RecommendationDTO> subjectMatches;
    private List<RecommendationDTO> trending;
    private List<RecommendationDTO> latestUpdates;
}
