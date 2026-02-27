package com.studentdashboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "total_sessions")
    private Integer totalSessions = 0;

    @Column(name = "attended_sessions")
    private Integer attendedSessions = 0;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Transient
    public Double getPercentage() {
        if (totalSessions == null || totalSessions == 0) {
            return 0.0;
        }
        return ((double) attendedSessions / totalSessions) * 100.0;
    }
}
