package com.studentdashboard.controller;

import com.studentdashboard.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Student Dashboard Backend is up and running!");
    }

    @GetMapping("/details/test")
    public ResponseEntity<String> testDetails() {
        return ResponseEntity.ok("Test Details Path OK");
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(studentService.getStudentById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
