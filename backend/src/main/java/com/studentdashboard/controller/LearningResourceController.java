package com.studentdashboard.controller;

import com.studentdashboard.model.LearningResource;
import com.studentdashboard.service.LearningResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "http://localhost:5173") // Allowing requests from Vue.js frontend
public class LearningResourceController {

    @Autowired
    private LearningResourceService service;

    /**
     * Endpoint to handle file uploads with metadata.
     * Receives file, subject, semester, and uploader name from frontend.
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subject") String subject,
            @RequestParam("semester") String semester,
            @RequestParam("uploadedBy") String uploadedBy) {
        try {
            // Save the resource and return success message
            service.saveResource(file, subject, semester, uploadedBy);
            return ResponseEntity.ok("File uploaded successfully. Awaiting admin approval.");
        } catch (Exception e) {
            // Return error message if the upload fails
            return ResponseEntity.badRequest().body("Error uploading file: " + e.getMessage());
        }
    }

    /**
     * Endpoint to fetch all resources that are approved by the admin.
     * This will be used to display resources to the students.
     */
    @GetMapping("/approved")
    public List<LearningResource> getApprovedResources() {
        return service.getApprovedResources();
    }
}