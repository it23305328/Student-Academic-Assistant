package com.studentdashboard.dto;

import java.time.LocalDateTime;

public class GroupMemberDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDateTime joinedAt;
    
    // Constructors
    public GroupMemberDTO() {}
    
    public GroupMemberDTO(Long id, String name, String email, String phone, LocalDateTime joinedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.joinedAt = joinedAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }
    
    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
}