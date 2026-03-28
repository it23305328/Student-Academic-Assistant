package com.studentdashboard.repository;

import com.studentdashboard.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    
    @Query("SELECT a FROM Announcement a WHERE " +
           "(:year IS NULL OR a.targetYear = 'all' OR a.targetYear = :year) AND " +
           "(:semester IS NULL OR a.targetSemester = 'all' OR a.targetSemester = :semester) AND " +
           "(:faculty IS NULL OR a.targetType = 'all' OR " +
           "(a.targetType = 'faculty' AND a.targetFaculty = :faculty) OR " +
           "(a.targetType = 'course' AND a.targetCourse = :course AND a.targetFaculty = :faculty)) " +
           "ORDER BY a.createdAt DESC")
    List<Announcement> findFilteredAnnouncements(@Param("year") String year,
                                                  @Param("semester") String semester,
                                                  @Param("faculty") String faculty,
                                                  @Param("course") String course);
}