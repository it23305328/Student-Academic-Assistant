package com.studentdashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpcomingAlertDTO {
    private String id;
    private String type; // 'Timetable' or 'Assignment'
    private String title;
    private String time; // Displayable time (e.g., "14:00 - 15:00 Today")
    private boolean urgent;
    private String message;
}
