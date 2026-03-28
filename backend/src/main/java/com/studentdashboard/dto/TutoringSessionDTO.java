package com.studentdashboard.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TutoringSessionDTO {
    private Long id;
    private String tutorName;
    private String tutorEmail;
    private String tutorPhone;
    private Integer year;
    private Integer semester;
    private String specialization;
    private String module;
    private String chapter;
    private LocalDate date;
    private LocalTime time;
    private String meetingLink;
    
    // Constructors
    public TutoringSessionDTO() {}
    
    public TutoringSessionDTO(Long id, String tutorName, String tutorEmail, String tutorPhone, Integer year, 
                             Integer semester, String specialization, String module, 
                             String chapter, LocalDate date, LocalTime time, String meetingLink) {
        this.id = id;
        this.tutorName = tutorName;
        this.tutorEmail = tutorEmail;
        this.tutorPhone = tutorPhone;
        this.year = year;
        this.semester = semester;
        this.specialization = specialization;
        this.module = module;
        this.chapter = chapter;
        this.date = date;
        this.time = time;
        this.meetingLink = meetingLink;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTutorName() {
        return tutorName;
    }
    
    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }
    
    public String getTutorEmail() {
        return tutorEmail;
    }
    
    public void setTutorEmail(String tutorEmail) {
        this.tutorEmail = tutorEmail;
    }
    
    public String getTutorPhone() {
        return tutorPhone;
    }
    
    public void setTutorPhone(String tutorPhone) {
        this.tutorPhone = tutorPhone;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public Integer getSemester() {
        return semester;
    }
    
    public void setSemester(Integer semester) {
        this.semester = semester;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getModule() {
        return module;
    }
    
    public void setModule(String module) {
        this.module = module;
    }
    
    public String getChapter() {
        return chapter;
    }
    
    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public LocalTime getTime() {
        return time;
    }
    
    public void setTime(LocalTime time) {
        this.time = time;
    }
    
    public String getMeetingLink() {
        return meetingLink;
    }
    
    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
    }
}