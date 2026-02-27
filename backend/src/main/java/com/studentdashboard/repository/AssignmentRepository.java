package com.studentdashboard.repository;

import com.studentdashboard.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByStudentIdOrderByDeadlineAsc(Long studentId);
}
