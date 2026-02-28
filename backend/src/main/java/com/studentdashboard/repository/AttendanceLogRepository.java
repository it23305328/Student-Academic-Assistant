package com.studentdashboard.repository;

import com.studentdashboard.model.AttendanceLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceLogRepository extends JpaRepository<AttendanceLog, Long> {
    Optional<AttendanceLog> findByStudentIdAndSubjectNameAndDate(Long studentId, String subjectName, LocalDate date);
}
