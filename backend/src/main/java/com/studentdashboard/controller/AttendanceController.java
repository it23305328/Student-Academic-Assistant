package com.studentdashboard.controller;

import com.studentdashboard.dto.AttendanceDTO;
import com.studentdashboard.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceForStudent(@PathVariable("studentId") Long studentId) {
        List<AttendanceDTO> attendanceRecords = attendanceService.getAttendanceForStudent(studentId);
        return new ResponseEntity<>(attendanceRecords, HttpStatus.OK);
    }

    @GetMapping("/summary/{studentId}")
    public ResponseEntity<List<AttendanceDTO>> getAttendanceSummary(@PathVariable("studentId") Long studentId) {
        List<AttendanceDTO> summary = attendanceService.getAttendanceSummary(studentId);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }

    @GetMapping("/weekly/{studentId}")
    public ResponseEntity<List<Map<String, Object>>> getWeeklyAttendance(@PathVariable("studentId") Long studentId) {
        List<Map<String, Object>> weeklyData = attendanceService.getWeeklyAttendance(studentId);
        return new ResponseEntity<>(weeklyData, HttpStatus.OK);
    }

    @PostMapping("/mark")
    public ResponseEntity<?> markAttendance(@RequestBody Map<String, Object> payload) {
        try {
            Long studentId = Long.valueOf(payload.get("studentId").toString());
            String subjectName = payload.get("subjectName").toString();
            boolean present = (boolean) payload.getOrDefault("present", true);

            Long timetableId = null;
            if (payload.containsKey("timetableId")) {
                timetableId = Long.valueOf(payload.get("timetableId").toString());
            }

            attendanceService.markAttendance(studentId, subjectName, present, timetableId);
            return new ResponseEntity<>("Attendance marked successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error marking attendance: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
