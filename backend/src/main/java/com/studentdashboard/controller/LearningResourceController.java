package com.studentdashboard.controller;

import com.studentdashboard.model.LearningResource;
import com.studentdashboard.service.LearningResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    /**
     * Endpoint to fetch all pending resources for the Admin Dashboard.
     */
    @GetMapping("/pending")
    public List<LearningResource> getPendingResources() {
        return service.getPendingResources();
    }

    /**
     * Endpoint to approve or reject a resource.
     */
    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id, @PathVariable("status") String status) {
        try {
            service.updateStatus(id, status);
            return ResponseEntity.ok("Resource status updated to: " + status);
        } catch (Throwable e) {
            java.io.StringWriter sw = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(sw));
            return ResponseEntity.status(500).body("Error: " + e.toString() + "\nTrace: " + sw.toString());
        }
    }

    /**
     * Endpoint to download a resource as an attachment.
     */
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadResource(@PathVariable("id") Long id) {
        LearningResource resource = service.getResourceById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(resource.getFileType()))
                .body(resource.getData());
    }

    /**
     * Endpoint to view a resource inline in the browser.
     */
    @GetMapping("/view/{id}")
    public ResponseEntity<byte[]> viewResource(@PathVariable("id") Long id) {
        LearningResource resource = service.getResourceById(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(resource.getFileType()))
                .body(resource.getData());
    }
}