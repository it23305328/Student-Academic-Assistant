package com.studentdashboard.controller;

import com.studentdashboard.dto.TutoringSessionDTO;
import com.studentdashboard.service.TutoringSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/tutoring")
public class TutoringSessionController {
    
    @Autowired
    private TutoringSessionService tutoringSessionService;
    
    @GetMapping("/sessions")
    public ResponseEntity<List<TutoringSessionDTO>> getAllSessions() {
        List<TutoringSessionDTO> sessions = tutoringSessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }
    
    @GetMapping("/sessions/{id}")
    public ResponseEntity<TutoringSessionDTO> getSessionById(@PathVariable Long id) {
        TutoringSessionDTO session = tutoringSessionService.getSessionById(id);
        return ResponseEntity.ok(session);
    }
    
    @PostMapping("/sessions/add")
    public ResponseEntity<TutoringSessionDTO> addSession(@RequestBody TutoringSessionDTO sessionDTO) {
        TutoringSessionDTO createdSession = tutoringSessionService.createSession(sessionDTO);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }
    
    @PutMapping("/sessions/update/{id}")
    public ResponseEntity<TutoringSessionDTO> updateSession(@PathVariable Long id, 
                                                            @RequestBody TutoringSessionDTO sessionDTO) {
        TutoringSessionDTO updatedSession = tutoringSessionService.updateSession(id, sessionDTO);
        return ResponseEntity.ok(updatedSession);
    }
    
    @DeleteMapping("/sessions/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteSession(@PathVariable Long id) {
        tutoringSessionService.deleteSession(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Tutoring session deleted successfully");
        response.put("id", id.toString());
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/sessions/filter")
    public ResponseEntity<List<TutoringSessionDTO>> getFilteredSessions(@RequestBody Map<String, Object> filterParams) {
        Integer year = filterParams.containsKey("year") ? (Integer) filterParams.get("year") : null;
        Integer semester = filterParams.containsKey("semester") ? (Integer) filterParams.get("semester") : null;
        String specialization = filterParams.containsKey("specialization") ? (String) filterParams.get("specialization") : null;
        
        List<TutoringSessionDTO> sessions = tutoringSessionService.getFilteredSessions(year, semester, specialization);
        return ResponseEntity.ok(sessions);
    }
    
    @GetMapping("/sessions/upcoming")
    public ResponseEntity<List<TutoringSessionDTO>> getUpcomingSessions() {
        List<TutoringSessionDTO> sessions = tutoringSessionService.getUpcomingSessions();
        return ResponseEntity.ok(sessions);
    }
}