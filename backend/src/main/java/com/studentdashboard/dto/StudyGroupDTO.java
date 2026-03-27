package com.studentdashboard.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class StudyGroupDTO {
    private Long id;
    private String creatorName;
    private String creatorEmail;
    private String creatorPhone;
    private Integer year;
    private Integer semester;
    private String specialization;
    private String module;
    private String topic;
    private String contactNumber;
    private Integer maxMembers;
    private Integer currentMembers;
    private LocalDateTime createdAt;
    private List<GroupMemberDTO> members;
    
    // Constructors
    public StudyGroupDTO() {}
    
    public StudyGroupDTO(Long id, String creatorName, String creatorEmail, String creatorPhone, Integer year, 
                        Integer semester, String specialization, String module, String topic, 
                        String contactNumber, Integer maxMembers, Integer currentMembers, 
                        LocalDateTime createdAt, List<GroupMemberDTO> members) {
        this.id = id;
        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.creatorPhone = creatorPhone;
        this.year = year;
        this.semester = semester;
        this.specialization = specialization;
        this.module = module;
        this.topic = topic;
        this.contactNumber = contactNumber;
        this.maxMembers = maxMembers;
        this.currentMembers = currentMembers;
        this.createdAt = createdAt;
        this.members = members;
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
    
    public String getContactNumber() {
        return contactNumber;
    }
    
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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
    
    public List<GroupMemberDTO> getMembers() {
        return members;
    }
    
    public void setMembers(List<GroupMemberDTO> members) {
        this.members = members;
    }
}