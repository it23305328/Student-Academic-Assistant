package com.studentdashboard.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "announcements")
public class Announcement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String topic;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "target_year")
    private String targetYear;
    
    @Column(name = "target_semester")
    private String targetSemester;
    
    @Column(name = "target_faculty")
    private String targetFaculty;
    
    @Column(name = "target_course")
    private String targetCourse;
    
    @Column(name = "target_type")
    private String targetType; // "all", "faculty", "course"
    
    @Column(name = "datetime")
    private LocalDateTime datetime;
    
    @Column(name = "image_url")
    private String imageUrl;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Constructors
    public Announcement() {}
    
    public Announcement(String topic, String content, String targetYear, 
                        String targetSemester, String targetFaculty, 
                        String targetCourse, String targetType, 
                        LocalDateTime datetime, String imageUrl) {
        this.topic = topic;
        this.content = content;
        this.targetYear = targetYear;
        this.targetSemester = targetSemester;
        this.targetFaculty = targetFaculty;
        this.targetCourse = targetCourse;
        this.targetType = targetType;
        this.datetime = datetime;
        this.imageUrl = imageUrl;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getTargetYear() {
        return targetYear;
    }
    
    public void setTargetYear(String targetYear) {
        this.targetYear = targetYear;
    }
    
    public String getTargetSemester() {
        return targetSemester;
    }
    
    public void setTargetSemester(String targetSemester) {
        this.targetSemester = targetSemester;
    }
    
    public String getTargetFaculty() {
        return targetFaculty;
    }
    
    public void setTargetFaculty(String targetFaculty) {
        this.targetFaculty = targetFaculty;
    }
    
    public String getTargetCourse() {
        return targetCourse;
    }
    
    public void setTargetCourse(String targetCourse) {
        this.targetCourse = targetCourse;
    }
    
    public String getTargetType() {
        return targetType;
    }
    
    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
    
    public LocalDateTime getDatetime() {
        return datetime;
    }
    
    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}