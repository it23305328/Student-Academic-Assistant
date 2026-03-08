package com.studentdashboard.controller;

import com.studentdashboard.model.Lecture;
import com.studentdashboard.service.LectureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/lectures")
// @PreAuthorize("hasRole('ADMIN')")
public class LectureController {

    @Autowired
    private LectureService lectureService;

    @GetMapping
    public List<Lecture> getAllLectures() {
        return lectureService.getAllLectures();
    }

    @PostMapping
    public Lecture saveLecture(@RequestBody Lecture lecture) {
        return lectureService.saveLecture(lecture);
    }

    @PutMapping("/{id}")
    public Lecture updateLecture(@PathVariable Long id, @RequestBody Lecture lectureDetails) {
        return lectureService.updateLecture(id, lectureDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLecture(@PathVariable Long id) {
        lectureService.deleteLecture(id);
        return ResponseEntity.ok().build();
    }
}
