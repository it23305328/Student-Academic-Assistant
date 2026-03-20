package com.studentdashboard.repository;

import com.studentdashboard.model.AttendanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Long> {
    Optional<AttendanceLog> findFirstByStudentIdAndSubjectNameAndDate(Long studentId, String subjectName,
            LocalDate date);

    Optional<AttendanceLog> findFirstByTimetableId(Long timetableId);

    java.util.List<AttendanceLog> findByStudentIdAndDateBetween(Long studentId, LocalDate startDate, LocalDate endDate);
}
