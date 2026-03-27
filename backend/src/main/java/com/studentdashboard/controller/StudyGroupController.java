package com.studentdashboard.controller;

import com.studentdashboard.dto.GroupMemberDTO;
import com.studentdashboard.dto.StudyGroupDTO;
import com.studentdashboard.service.StudyGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/study-groups")
public class StudyGroupController {
    
    @Autowired
    private StudyGroupService studyGroupService;
    
    @GetMapping("/groups")
    public ResponseEntity<List<StudyGroupDTO>> getAllGroups() {
        List<StudyGroupDTO> groups = studyGroupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }
    
    @GetMapping("/groups/{id}")
    public ResponseEntity<StudyGroupDTO> getGroupById(@PathVariable Long id) {
        StudyGroupDTO group = studyGroupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }
    
    @PostMapping("/groups/create")
    public ResponseEntity<StudyGroupDTO> createGroup(@RequestBody StudyGroupDTO groupDTO) {
        StudyGroupDTO createdGroup = studyGroupService.createGroup(groupDTO);
        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }
    
    @PostMapping("/groups/join/{id}")
    public ResponseEntity<StudyGroupDTO> joinGroup(@PathVariable Long id, 
                                                    @RequestBody GroupMemberDTO memberDTO) {
        StudyGroupDTO updatedGroup = studyGroupService.joinGroup(id, memberDTO);
        return ResponseEntity.ok(updatedGroup);
    }
    
    @DeleteMapping("/groups/leave/{id}")
    public ResponseEntity<StudyGroupDTO> leaveGroup(@PathVariable Long id, 
                                                    @RequestParam String userEmail) {
        StudyGroupDTO updatedGroup = studyGroupService.leaveGroup(id, userEmail);
        return ResponseEntity.ok(updatedGroup);
    }
    
    @DeleteMapping("/groups/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteGroup(@PathVariable Long id, 
                                                            @RequestParam String creatorEmail) {
        studyGroupService.deleteGroup(id, creatorEmail);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Study group deleted successfully");
        response.put("id", id.toString());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/groups/creator/{email}")
    public ResponseEntity<List<StudyGroupDTO>> getGroupsByCreator(@PathVariable String email) {
        List<StudyGroupDTO> groups = studyGroupService.getGroupsByCreator(email);
        return ResponseEntity.ok(groups);
    }
    
    @PostMapping("/groups/filter")
    public ResponseEntity<List<StudyGroupDTO>> getFilteredGroups(@RequestBody Map<String, Object> filterParams) {
        Integer year = filterParams.containsKey("year") ? (Integer) filterParams.get("year") : null;
        Integer semester = filterParams.containsKey("semester") ? (Integer) filterParams.get("semester") : null;
        String specialization = filterParams.containsKey("specialization") ? (String) filterParams.get("specialization") : null;
        
        List<StudyGroupDTO> groups = studyGroupService.getFilteredGroups(year, semester, specialization);
        return ResponseEntity.ok(groups);
    }
}