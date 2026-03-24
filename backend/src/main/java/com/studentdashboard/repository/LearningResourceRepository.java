package com.studentdashboard.repository;

import com.studentdashboard.model.LearningResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LearningResourceRepository extends JpaRepository<LearningResource, Long> {

    // Find resources by their status (e.g., pending, approved, rejected)
    List<LearningResource> findByStatus(String status);

    // Filter resources by subject, semester, and status
    List<LearningResource> findBySubjectAndSemesterAndStatus(String subject, String semester, String status);
}
