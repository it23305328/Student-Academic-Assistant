package com.studentdashboard.service;

import com.studentdashboard.model.LearningResource;
import com.studentdashboard.repository.LearningResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Service
public class LearningResourceService {

    @Autowired
    private LearningResourceRepository repository;

    /**
     * Method to save a new learning resource file to the database.
     * It extracts file metadata and sets the initial status to 'pending'.
     */
    public LearningResource saveResource(MultipartFile file, String subject, String semester, String uploadedBy)
            throws IOException {
        LearningResource resource = new LearningResource();
        resource.setFileName(file.getOriginalFilename());
        resource.setFileType(file.getContentType());
        resource.setData(file.getBytes()); // Convert file to byte array to store in LOB
        resource.setSubject(subject);
        resource.setSemester(semester);
        resource.setUploadedBy(uploadedBy);
        resource.setStatus("pending"); // Default status for admin approval workflow

        return repository.save(resource);
    }

    /**
     * Retrieve all learning resources that have been approved by the admin.
     */
    public List<LearningResource> getApprovedResources() {
        return repository.findByStatus("approved");
    }

    /**
     * Retrieve all resources that are currently in 'pending' status for admin
     * review.
     */
    public List<LearningResource> getPendingResources() {
        return repository.findByStatus("pending");
    }

    /**
     * Update the status of a specific resource (e.g., to 'approved' or 'rejected').
     */
    public LearningResource updateStatus(Long id, String status) {
        LearningResource resource = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found with id: " + id));
        resource.setStatus(status);
        return repository.save(resource);
    }
}