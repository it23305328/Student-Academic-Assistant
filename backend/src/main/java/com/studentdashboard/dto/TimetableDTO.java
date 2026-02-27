package com.studentdashboard.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimetableDTO {
    private Long id;
    private String subjectName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String venue;

    public TimetableDTO() {
    }

    public TimetableDTO(Long id, String subjectName, LocalDate date, LocalTime startTime, LocalTime endTime,
            String venue) {
        this.id = id;
        this.subjectName = subjectName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }
}
