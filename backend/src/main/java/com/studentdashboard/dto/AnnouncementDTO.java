package com.studentdashboard.dto;

import java.time.LocalDateTime;

public class AnnouncementDTO {
    private Long id;
    private String topic;
    private String content;
    private String targetYear;
    private String targetSemester;
    private String targetFaculty;
    private String targetCourse;
    private String targetType;
    private LocalDateTime datetime;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public AnnouncementDTO() {}
    
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