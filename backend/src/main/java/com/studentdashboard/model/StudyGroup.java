package com.studentdashboard.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "study_groups")
public class StudyGroup {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String creatorName;
    
    @Column(nullable = false)  // Removed unique = true
    private String creatorEmail;
    
    @Column(nullable = false)
    private String creatorPhone;
    
    @Column(nullable = false)
    private Integer year;
    
    @Column(nullable = false)
    private Integer semester;
    
    @Column(nullable = false)
    private String specialization;
    
    @Column(nullable = false)
    private String module;
    
    @Column(nullable = false)
    private String topic;
    
    @Column(nullable = false)
    private Integer maxMembers;
    
    @Column(nullable = false)
    private Integer currentMembers = 1;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupMember> members = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public StudyGroup() {}
    
    public StudyGroup(String creatorName, String creatorEmail, String creatorPhone, Integer year, Integer semester, 
                     String specialization, String module, String topic, Integer maxMembers) {
        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.creatorPhone = creatorPhone;
        this.year = year;
        this.semester = semester;
        this.specialization = specialization;
        this.module = module;
        this.topic = topic;
        this.maxMembers = maxMembers;
        this.currentMembers = 1;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCreatorName() {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    
    public String getCreatorEmail() {
        return creatorEmail;
    }
    
    public void setCreatorEmail(String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }
    
    public String getCreatorPhone() {
        return creatorPhone;
    }
    
    public void setCreatorPhone(String creatorPhone) {
        this.creatorPhone = creatorPhone;
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
    
    public String getTopic() {
        return topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    public Integer getMaxMembers() {
        return maxMembers;
    }
    
    public void setMaxMembers(Integer maxMembers) {
        this.maxMembers = maxMembers;
    }
    
    public Integer getCurrentMembers() {
        return currentMembers;
    }
    
    public void setCurrentMembers(Integer currentMembers) {
        this.currentMembers = currentMembers;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<GroupMember> getMembers() {
        return members;
    }
    
    public void setMembers(List<GroupMember> members) {
        this.members = members;
    }
}