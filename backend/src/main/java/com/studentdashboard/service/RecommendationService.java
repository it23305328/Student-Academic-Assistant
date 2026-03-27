package com.studentdashboard.service;

import com.studentdashboard.dto.RecommendationDTO;
import com.studentdashboard.dto.RecommendationsResponseDTO;
import com.studentdashboard.model.Lecture;
import com.studentdashboard.model.Timetable;
import com.studentdashboard.repository.LectureRepository;
import com.studentdashboard.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    public RecommendationsResponseDTO getRecommendationsForStudent(Long studentId) {
        // For testing - return simple hardcoded recommendations
        List<RecommendationDTO> subjectMatches = new ArrayList<>();
        subjectMatches.add(new RecommendationDTO("Introduction to OOP", "Match for your current subject schedule", "Lecture Library", "https://youtube.com/example1", "Subject Match"));

        List<RecommendationDTO> trending = new ArrayList<>();
        trending.add(new RecommendationDTO("AI Basics", "Trending now", "Lecture Trending", "https://youtube.com/example3", "Trending"));

        List<RecommendationDTO> latestUpdates = new ArrayList<>();
        latestUpdates.add(new RecommendationDTO("Web Development", "Newly uploaded", "Latest", "https://youtube.com/example5", "Latest Update"));

        return new RecommendationsResponseDTO(subjectMatches, trending, latestUpdates);
    }
}
