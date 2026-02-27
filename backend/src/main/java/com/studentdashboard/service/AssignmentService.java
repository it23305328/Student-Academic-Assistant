package com.studentdashboard.service;

import com.studentdashboard.model.Assignment;
import com.studentdashboard.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    public List<Assignment> getAssignmentsByStudentId(Long studentId) {
        return assignmentRepository.findByStudentIdOrderByDeadlineAsc(studentId);
    }

    public Assignment addAssignment(Assignment assignment) {
        if (assignment.getStatus() == null) {
            assignment.setStatus("Pending");
        }
        return assignmentRepository.save(assignment);
    }
}
