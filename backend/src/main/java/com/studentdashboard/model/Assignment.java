package com.studentdashboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false)
    private String status; // e.g., Pending, Completed

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Transient
    public String getFormattedCountdown() {
        if (deadline == null)
            return "No Deadline";
        LocalDateTime now = LocalDateTime.now();
        java.time.Duration duration = java.time.Duration.between(now, deadline);
        if (duration.isNegative())
            return "Overdue";

        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();

        return String.format("%02dd : %02dh : %02dm", days, hours, minutes);
    }
}
