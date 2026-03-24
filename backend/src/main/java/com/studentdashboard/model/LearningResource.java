package com.studentdashboard.model;

import jakarta.persistence.*;

@Entity
@Table(name = "learning_resources")
public class LearningResource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;
    private String uploadedBy;
    private String subject;
    private String semester;
    private String status = "pending";

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    public LearningResource() {}

    public LearningResource(Long id, String fileName, String fileType, String uploadedBy, String subject, String semester, String status, byte[] data) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.uploadedBy = uploadedBy;
        this.subject = subject;
        this.semester = semester;
        this.status = status;
        this.data = data;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public String getUploadedBy() { return uploadedBy; }
    public void setUploadedBy(String uploadedBy) { this.uploadedBy = uploadedBy; }
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public byte[] getData() { return data; }
    public void setData(byte[] data) { this.data = data; }
}