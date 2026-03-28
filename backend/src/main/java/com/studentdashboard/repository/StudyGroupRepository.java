package com.studentdashboard.repository;

import com.studentdashboard.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroup, Long> {
    
    List<StudyGroup> findByOrderByCreatedAtDesc();
    
    List<StudyGroup> findByYearAndSemester(Integer year, Integer semester);
    
    List<StudyGroup> findBySpecialization(String specialization);
    
    List<StudyGroup> findByCreatorEmail(String creatorEmail);
    
    @Query("SELECT sg FROM StudyGroup sg WHERE sg.year = :year AND sg.semester = :semester AND sg.specialization = :specialization")
    List<StudyGroup> findByStudentInfo(@Param("year") Integer year, 
                                       @Param("semester") Integer semester, 
                                       @Param("specialization") String specialization);
}