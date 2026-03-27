package com.studentdashboard.controller;

import com.studentdashboard.dto.AnnouncementDTO;
import com.studentdashboard.dto.StudentFilterDTO;
import com.studentdashboard.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {
    
    @Autowired
    private AnnouncementService announcementService;
    
    @GetMapping
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        return ResponseEntity.ok(announcementService.getAllAnnouncements());
    }
    
    @PostMapping("/add")
    public ResponseEntity<AnnouncementDTO> addAnnouncement(
            @RequestPart("announcement") AnnouncementDTO announcementDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            AnnouncementDTO saved = announcementService.addAnnouncement(announcementDTO, imageFile);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<AnnouncementDTO> updateAnnouncement(
            @PathVariable Long id,
            @RequestPart("announcement") AnnouncementDTO announcementDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            AnnouncementDTO updated = announcementService.updateAnnouncement(id, announcementDTO, imageFile);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAnnouncement(@PathVariable Long id) {
        try {
            announcementService.deleteAnnouncement(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/filter")
    public ResponseEntity<List<AnnouncementDTO>> getFilteredAnnouncements(@RequestBody StudentFilterDTO filter) {
        try {
            List<AnnouncementDTO> filtered = announcementService.getFilteredAnnouncements(filter);
            return ResponseEntity.ok(filtered);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}