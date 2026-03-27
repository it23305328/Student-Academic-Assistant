package com.studentdashboard.repository;

import com.studentdashboard.model.TutoringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TutoringSessionRepository extends JpaRepository<TutoringSession, Long> {
    
    List<TutoringSession> findByOrderByCreatedAtDesc();
    
    List<TutoringSession> findByYearAndSemester(Integer year, Integer semester);
    
    List<TutoringSession> findBySpecialization(String specialization);
    
    List<TutoringSession> findByDateAfter(LocalDate date);
    
    @Query("SELECT t FROM TutoringSession t WHERE t.year = :year AND t.semester = :semester AND t.specialization = :specialization")
    List<TutoringSession> findByStudentInfo(@Param("year") Integer year, 
                                            @Param("semester") Integer semester, 
                                            @Param("specialization") String specialization);
}