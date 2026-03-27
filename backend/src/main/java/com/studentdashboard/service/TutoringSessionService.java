package com.studentdashboard.service;

import com.studentdashboard.dto.TutoringSessionDTO;
import com.studentdashboard.model.TutoringSession;
import com.studentdashboard.repository.TutoringSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TutoringSessionService {
    
    @Autowired
    private TutoringSessionRepository tutoringSessionRepository;
    
    public List<TutoringSessionDTO> getAllSessions() {
        return tutoringSessionRepository.findByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public TutoringSessionDTO getSessionById(Long id) {
        TutoringSession session = tutoringSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoring session not found with id: " + id));
        return convertToDTO(session);
    }
    
    public TutoringSessionDTO createSession(TutoringSessionDTO sessionDTO) {
        // Remove the email uniqueness check to allow multiple sessions per user
        TutoringSession session = convertToEntity(sessionDTO);
        TutoringSession savedSession = tutoringSessionRepository.save(session);
        return convertToDTO(savedSession);
    }
    
    public TutoringSessionDTO updateSession(Long id, TutoringSessionDTO sessionDTO) {
        TutoringSession existingSession = tutoringSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoring session not found with id: " + id));
        
        existingSession.setTutorName(sessionDTO.getTutorName());
        existingSession.setTutorEmail(sessionDTO.getTutorEmail());
        existingSession.setTutorPhone(sessionDTO.getTutorPhone());
        existingSession.setYear(sessionDTO.getYear());
        existingSession.setSemester(sessionDTO.getSemester());
        existingSession.setSpecialization(sessionDTO.getSpecialization());
        existingSession.setModule(sessionDTO.getModule());
        existingSession.setChapter(sessionDTO.getChapter());
        existingSession.setDate(sessionDTO.getDate());
        existingSession.setTime(sessionDTO.getTime());
        existingSession.setMeetingLink(sessionDTO.getMeetingLink());
        
        TutoringSession updatedSession = tutoringSessionRepository.save(existingSession);
        return convertToDTO(updatedSession);
    }
    
    public void deleteSession(Long id) {
        TutoringSession session = tutoringSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoring session not found with id: " + id));
        tutoringSessionRepository.delete(session);
    }
    
    public List<TutoringSessionDTO> getFilteredSessions(Integer year, Integer semester, String specialization) {
        if (year != null && semester != null && specialization != null && !specialization.isEmpty()) {
            return tutoringSessionRepository.findByStudentInfo(year, semester, specialization)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else if (year != null && semester != null) {
            return tutoringSessionRepository.findByYearAndSemester(year, semester)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else if (specialization != null && !specialization.isEmpty()) {
            return tutoringSessionRepository.findBySpecialization(specialization)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return getAllSessions();
        }
    }
    
    public List<TutoringSessionDTO> getUpcomingSessions() {
        return tutoringSessionRepository.findByDateAfter(LocalDate.now().minusDays(1))
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private TutoringSessionDTO convertToDTO(TutoringSession session) {
        return new TutoringSessionDTO(
                session.getId(),
                session.getTutorName(),
                session.getTutorEmail(),
                session.getTutorPhone(),
                session.getYear(),
                session.getSemester(),
                session.getSpecialization(),
                session.getModule(),
                session.getChapter(),
                session.getDate(),
                session.getTime(),
                session.getMeetingLink()
        );
    }
    
    private TutoringSession convertToEntity(TutoringSessionDTO dto) {
        return new TutoringSession(
                dto.getTutorName(),
                dto.getTutorEmail(),
                dto.getTutorPhone(),
                dto.getYear(),
                dto.getSemester(),
                dto.getSpecialization(),
                dto.getModule(),
                dto.getChapter(),
                dto.getDate(),
                dto.getTime(),
                dto.getMeetingLink()
        );
    }
}