package com.studentdashboard.controller;

import com.studentdashboard.model.Assignment;
import com.studentdashboard.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<Assignment>> getAssignmentsByStudentId(@PathVariable("studentId") Long studentId) {
        List<Assignment> assignments = assignmentService.getAssignmentsByStudentId(studentId);
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAssignment(@RequestBody Assignment assignment) {
        try {
            Assignment addedAssignment = assignmentService.addAssignment(assignment);
            return new ResponseEntity<>(addedAssignment, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding assignment: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
