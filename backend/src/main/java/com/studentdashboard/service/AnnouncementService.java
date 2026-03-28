package com.studentdashboard.service;

import com.studentdashboard.dto.AnnouncementDTO;
import com.studentdashboard.dto.StudentFilterDTO;
import com.studentdashboard.model.Announcement;
import com.studentdashboard.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {
    
    @Autowired
    private AnnouncementRepository announcementRepository;
    
    @Autowired
    private CloudinaryService cloudinaryService;
    
    public List<AnnouncementDTO> getAllAnnouncements() {
        return announcementRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public AnnouncementDTO addAnnouncement(AnnouncementDTO announcementDTO, MultipartFile imageFile) {
        try {
            String imageUrl = null;
            if (imageFile != null && !imageFile.isEmpty()) {
                imageUrl = cloudinaryService.uploadImage(imageFile);
            }
            
            Announcement announcement = new Announcement();
            announcement.setTopic(announcementDTO.getTopic());
            announcement.setContent(announcementDTO.getContent());
            announcement.setTargetYear(announcementDTO.getTargetYear());
            announcement.setTargetSemester(announcementDTO.getTargetSemester());
            announcement.setTargetFaculty(announcementDTO.getTargetFaculty());
            announcement.setTargetCourse(announcementDTO.getTargetCourse());
            announcement.setTargetType(announcementDTO.getTargetType());
            announcement.setDatetime(announcementDTO.getDatetime());
            announcement.setImageUrl(imageUrl);
            announcement.setCreatedAt(LocalDateTime.now());
            announcement.setUpdatedAt(LocalDateTime.now());
            
            Announcement saved = announcementRepository.save(announcement);
            return convertToDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add announcement: " + e.getMessage());
        }
    }
    
    public AnnouncementDTO updateAnnouncement(Long id, AnnouncementDTO announcementDTO, MultipartFile imageFile) {
        try {
            Announcement existing = announcementRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Announcement not found"));
            
            existing.setTopic(announcementDTO.getTopic());
            existing.setContent(announcementDTO.getContent());
            existing.setTargetYear(announcementDTO.getTargetYear());
            existing.setTargetSemester(announcementDTO.getTargetSemester());
            existing.setTargetFaculty(announcementDTO.getTargetFaculty());
            existing.setTargetCourse(announcementDTO.getTargetCourse());
            existing.setTargetType(announcementDTO.getTargetType());
            existing.setDatetime(announcementDTO.getDatetime());
            
            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = cloudinaryService.uploadImage(imageFile);
                existing.setImageUrl(imageUrl);
            } else if (announcementDTO.getImageUrl() != null) {
                existing.setImageUrl(announcementDTO.getImageUrl());
            }
            
            existing.setUpdatedAt(LocalDateTime.now());
            
            Announcement updated = announcementRepository.save(existing);
            return convertToDTO(updated);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update announcement: " + e.getMessage());
        }
    }
    
    public void deleteAnnouncement(Long id) {
        announcementRepository.deleteById(id);
    }
    
    public List<AnnouncementDTO> getFilteredAnnouncements(StudentFilterDTO filter) {
        return announcementRepository.findFilteredAnnouncements(
                filter.getYear(),
                filter.getSemester(),
                filter.getFaculty(),
                filter.getCourse()
        ).stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    private AnnouncementDTO convertToDTO(Announcement announcement) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setId(announcement.getId());
        dto.setTopic(announcement.getTopic());
        dto.setContent(announcement.getContent());
        dto.setTargetYear(announcement.getTargetYear());
        dto.setTargetSemester(announcement.getTargetSemester());
        dto.setTargetFaculty(announcement.getTargetFaculty());
        dto.setTargetCourse(announcement.getTargetCourse());
        dto.setTargetType(announcement.getTargetType());
        dto.setDatetime(announcement.getDatetime());
        dto.setImageUrl(announcement.getImageUrl());
        dto.setCreatedAt(announcement.getCreatedAt());
        dto.setUpdatedAt(announcement.getUpdatedAt());
        return dto;
    }
}