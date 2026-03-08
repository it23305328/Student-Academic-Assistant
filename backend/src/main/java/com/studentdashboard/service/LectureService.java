package com.studentdashboard.service;

import com.studentdashboard.model.Lecture;
import com.studentdashboard.repository.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LectureService {

    @Autowired
    private LectureRepository lectureRepository;

    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }

    public Lecture saveLecture(Lecture lecture) {
        return lectureRepository.save(lecture);
    }

    public void deleteLecture(Long id) {
        lectureRepository.deleteById(id);
    }

    public Lecture updateLecture(Long id, Lecture lectureDetails) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecture not found"));

        lecture.setAcademicYear(lectureDetails.getAcademicYear());
        lecture.setSemester(lectureDetails.getSemester());
        lecture.setCourse(lectureDetails.getCourse());
        lecture.setModuleName(lectureDetails.getModuleName());
        lecture.setTitle(lectureDetails.getTitle());
        lecture.setYoutubeUrl(lectureDetails.getYoutubeUrl());
        lecture.setDescription(lectureDetails.getDescription());

        return lectureRepository.save(lecture);
    }
}
