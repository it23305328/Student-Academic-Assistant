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
        List<Timetable> timetable = timetableRepository.findByStudentId(studentId);
        List<Lecture> lectures = lectureRepository.findAll();

        // 1. Subject matching
        Set<String> studentSubjects = timetable.stream()
                .map(Timetable::getSubjectName)
                .collect(Collectors.toSet());

        List<RecommendationDTO> subjectMatches = lectures.stream()
                .filter(lecture -> studentSubjects.stream().anyMatch(subject ->
                        lecture.getModuleName().equalsIgnoreCase(subject)
                                || lecture.getTitle().equalsIgnoreCase(subject)
                                || lecture.getCourse().equalsIgnoreCase(subject)
                ))
                .limit(5)
                .map(lecture -> new RecommendationDTO(
                        lecture.getTitle(),
                        "Match for your current subject schedule: " + lecture.getModuleName(),
                        "Lecture Library",
                        lecture.getYoutubeUrl(),
                        "Subject Match"
                ))
                .collect(Collectors.toList());

        if (subjectMatches.isEmpty()) {
            // fall back to a few no-match recommendations
            subjectMatches = lectures.stream()
                    .limit(3)
                    .map(lecture -> new RecommendationDTO(
                            lecture.getTitle(),
                            "Suggested content for expanding your learning.",
                            "Lecture Library",
                            lecture.getYoutubeUrl(),
                            "Subject Match"
                    ))
                    .collect(Collectors.toList());
        }

        // 2. Trending (most recently added by id descending, for demo as no timestamp)
        List<RecommendationDTO> trending = lectures.stream()
                .sorted(Comparator.comparingLong(Lecture::getId).reversed())
                .limit(4)
                .map(lecture -> new RecommendationDTO(
                        lecture.getTitle(),
                        "Trending now based on community views & relevance.",
                        "Lecture Trending",
                        lecture.getYoutubeUrl(),
                        "Trending"
                ))
                .collect(Collectors.toList());

        // 3. Latest updates (also by id)
        List<RecommendationDTO> latestUpdates = lectures.stream()
                .sorted(Comparator.comparingLong(Lecture::getId).reversed())
                .limit(4)
                .map(lecture -> new RecommendationDTO(
                        lecture.getTitle(),
                        "Newly uploaded material available now.",
                        "Latest", lecture.getYoutubeUrl(),
                        "Latest Update"
                ))
                .collect(Collectors.toList());

        return new RecommendationsResponseDTO(subjectMatches, trending, latestUpdates);
    }
}
