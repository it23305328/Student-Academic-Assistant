package com.studentdashboard.service;

import com.studentdashboard.dto.AttendanceDTO;
import com.studentdashboard.model.Attendance;
import com.studentdashboard.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private com.studentdashboard.repository.AttendanceLogRepository attendanceLogRepository;

    @Autowired
    private com.studentdashboard.repository.TimetableRepository timetableRepository;

    public List<AttendanceDTO> getAttendanceForStudent(Long studentId) {
        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
        List<com.studentdashboard.model.Timetable> allTimetable = timetableRepository
                .findByStudentIdOrderByDateAscStartTimeAsc(studentId);

        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalTime now = java.time.LocalTime.now();

        return attendances.stream().map(attendance -> {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(attendance.getId());
            dto.setStudentId(attendance.getStudentId());
            dto.setSubjectName(attendance.getSubjectName());

            // Task 1 Logic: Dynamic Expected Sessions
            long expectedSessions = allTimetable.stream()
                    .filter(t -> t.getSubjectName().equalsIgnoreCase(attendance.getSubjectName()))
                    .filter(t -> {
                        if (t.getDate().isBefore(today))
                            return true;
                        if (t.getDate().isEqual(today) && t.getEndTime().isBefore(now))
                            return true;
                        return false;
                    }).count();

            // Attended Sessions is the manual count from DB
            int attended = attendance.getAttendedSessions();

            // If student marked present for a class that is currently happening or hasn't
            // started,
            // we should adjust expectedSessions so percentage doesn't exceed 100%
            if (attended > expectedSessions) {
                expectedSessions = attended;
            }

            dto.setTotalSessions((int) expectedSessions);
            dto.setAttendedSessions(attended);

            double percentage = expectedSessions == 0 ? 0.0 : ((double) attended / expectedSessions) * 100.0;
            dto.setPercentage(percentage);
            dto.setLowAttendanceWarning(percentage < 80.0);

            return dto;
        }).collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendanceSummary(Long studentId) {
        return getAttendanceForStudent(studentId);
    }

    public void markAttendance(Long studentId, String subjectName, boolean present) {
        java.time.LocalDate today = java.time.LocalDate.now();

        // Track daily log so we know if they marked for today
        java.util.Optional<com.studentdashboard.model.AttendanceLog> logOpt = attendanceLogRepository
                .findByStudentIdAndSubjectNameAndDate(studentId, subjectName, today);

        if (logOpt.isPresent()) {
            return; // Already marked for today
        }

        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);
        Attendance attendance = attendances.stream()
                .filter(a -> a.getSubjectName().equalsIgnoreCase(subjectName))
                .findFirst()
                .orElseGet(() -> {
                    Attendance newAttr = new Attendance();
                    newAttr.setStudentId(studentId);
                    newAttr.setSubjectName(subjectName);
                    newAttr.setTotalSessions(0);
                    newAttr.setAttendedSessions(0);
                    return newAttr;
                });

        if (present) {
            attendance.setAttendedSessions(attendance.getAttendedSessions() + 1);

            com.studentdashboard.model.AttendanceLog log = new com.studentdashboard.model.AttendanceLog();
            log.setStudentId(studentId);
            log.setSubjectName(subjectName);
            log.setDate(today);
            attendanceLogRepository.save(log);
        }

        // We still increment totalSessions in DB just for legacy,
        // but our dynamic calc will override it in the DTO
        attendance.setTotalSessions(attendance.getTotalSessions() + 1);
        attendanceRepository.save(attendance);
    }
}
