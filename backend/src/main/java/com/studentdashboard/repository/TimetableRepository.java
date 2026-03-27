package com.studentdashboard.repository;

import com.studentdashboard.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {
    List<Timetable> findByStudentIdOrderByDateAscStartTimeAsc(Long studentId);

    List<Timetable> findByStudentIdAndDateOrderByStartTimeAsc(Long studentId, LocalDate date);

    List<Timetable> findByStudentId(Long studentId);
}
