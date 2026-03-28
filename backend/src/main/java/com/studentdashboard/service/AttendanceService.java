package com.studentdashboard.service;

import com.studentdashboard.dto.AttendanceDTO;
import com.studentdashboard.model.Attendance;
import com.studentdashboard.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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

    public void markAttendance(Long studentId, String subjectName, boolean present, Long timetableId) {
        java.time.LocalDate today = java.time.LocalDate.now();

        if (timetableId != null) {
            java.util.Optional<com.studentdashboard.model.AttendanceLog> logOpt = attendanceLogRepository
                    .findFirstByTimetableId(timetableId);
            if (logOpt.isPresent()) {
                return; // Already marked for this specific session
            }
        } else {
            java.util.Optional<com.studentdashboard.model.AttendanceLog> logOpt = attendanceLogRepository
                    .findFirstByStudentIdAndSubjectNameAndDate(studentId, subjectName, today);
            if (logOpt.isPresent()) {
                return; // Already marked for today (fallback)
            }
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
            log.setTimetableId(timetableId);
            attendanceLogRepository.save(log);
        }

        // We still increment totalSessions in DB just for legacy,
        // but our dynamic calc will override it in the DTO
        attendance.setTotalSessions(attendance.getTotalSessions() + 1);
        attendanceRepository.save(attendance);
    }

    public List<Map<String, Object>> getWeeklyAttendance(Long studentId) {
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate weekAgo = today.minusDays(6);

        List<com.studentdashboard.model.Timetable> weekTimetables = timetableRepository
                .findByStudentIdAndDateBetween(studentId, weekAgo, today);

        List<com.studentdashboard.model.AttendanceLog> weekLogs = attendanceLogRepository
                .findByStudentIdAndDateBetween(studentId, weekAgo, today);

        List<Map<String, Object>> result = new java.util.ArrayList<>();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("EEE");

        for (int i = 6; i >= 0; i--) {
            java.time.LocalDate currentDate = today.minusDays(i);
            String dayName = currentDate.format(formatter);

            long expectedForDay = weekTimetables.stream()
                    .filter(t -> t.getDate().isEqual(currentDate) &&
                            (currentDate.isBefore(today) || (currentDate.isEqual(today)
                                    && t.getEndTime().isBefore(java.time.LocalTime.now()))))
                    .count();

            long attendedForDay = weekLogs.stream()
                    .filter(l -> l.getDate().isEqual(currentDate))
                    .count();

            // Limit attended to expected theoretically, though ideally 1:1
            if (attendedForDay > expectedForDay)
                attendedForDay = expectedForDay;

            int percentage = 100;
            if (expectedForDay > 0) {
                percentage = (int) Math.round(((double) attendedForDay / expectedForDay) * 100);
            } else {
                percentage = 100; // If no classes, consider it 100% or 0%. Let's default to full for
                                  // visualization if there are no classes scheduled that day, to avoid awkward 0
                                  // drops. Or maybe 0? To match the chart, 0% looks bad if there's no class.
                                  // Let's do 100 if no class, or just keep previous day's or 0. Let's do 0 for no
                                  // classes if we want but it drops the chart. Actually, let's just do 0 so it's
                                  // accurate, or null. Let's do 0.
                percentage = 0;
            }

            Map<String, Object> dayData = new java.util.HashMap<>();
            dayData.put("name", dayName);
            dayData.put("value", expectedForDay == 0 ? 0 : percentage); // Using 0 if no class

            result.add(dayData);
        }

        return result;
    }
}
