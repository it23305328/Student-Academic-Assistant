package com.studentdashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {
    private Long id;
    private Long studentId;
    private String subjectName;
    private Integer totalSessions;
    private Integer attendedSessions;
    private Double percentage;
    private boolean lowAttendanceWarning;
}
