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

    public List<AttendanceDTO> getAttendanceForStudent(Long studentId) {
        List<Attendance> attendances = attendanceRepository.findByStudentId(studentId);

        return attendances.stream().map(attendance -> {
            AttendanceDTO dto = new AttendanceDTO();
            dto.setId(attendance.getId());
            dto.setStudentId(attendance.getStudentId());
            dto.setSubjectName(attendance.getSubjectName());
            dto.setTotalSessions(attendance.getTotalSessions());
            dto.setAttendedSessions(attendance.getAttendedSessions());

            double percentage = attendance.getPercentage();
            dto.setPercentage(percentage);

            // If percentage is less than 80, set lowAttendanceWarning to true
            dto.setLowAttendanceWarning(percentage < 80.0);

            return dto;
        }).collect(Collectors.toList());
    }

    public List<AttendanceDTO> getAttendanceSummary(Long studentId) {
        return getAttendanceForStudent(studentId);
    }

    public void markAttendance(Long studentId, String subjectName, boolean present) {
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

        attendance.setTotalSessions(attendance.getTotalSessions() + 1);
        if (present) {
            attendance.setAttendedSessions(attendance.getAttendedSessions() + 1);
        }
        attendanceRepository.save(attendance);
    }
}
