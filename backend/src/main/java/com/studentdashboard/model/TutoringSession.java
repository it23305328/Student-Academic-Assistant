package com.studentdashboard.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "tutoring_sessions")
public class TutoringSession {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String tutorName;
    
    @Column(nullable = false)
    private String tutorEmail;
    
    @Column(nullable = false)
    private String tutorPhone;
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(nullable = false)
    private Integer semester;
    
    @Column(nullable = false)
    private String specialization;
    
    @Column(nullable = false)
    private String module;
    
    @Column(nullable = false)
    private String chapter;
    
    @Column(nullable = false)
    private LocalDate date;
    
    @Column(nullable = false)
    private LocalTime time;
    
    @Column(nullable = false)
    private String meetingLink;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public TutoringSession() {}
    
    public TutoringSession(String tutorName, String tutorEmail, String tutorPhone, Integer year, Integer semester, 
                          String specialization, String module, String chapter, LocalDate date, 
                          LocalTime time, String meetingLink) {
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}