package com.studentdashboard.service;

import com.studentdashboard.dto.GroupMemberDTO;
import com.studentdashboard.dto.StudyGroupDTO;
import com.studentdashboard.model.GroupMember;
import com.studentdashboard.model.StudyGroup;
import com.studentdashboard.repository.GroupMemberRepository;
import com.studentdashboard.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudyGroupService {
    
    @Autowired
    private StudyGroupRepository studyGroupRepository;
    
    @Autowired
    private GroupMemberRepository groupMemberRepository;
    
    public List<StudyGroupDTO> getAllGroups() {
        return studyGroupRepository.findByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public StudyGroupDTO getGroupById(Long id) {
        StudyGroup group = studyGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Study group not found with id: " + id));
        return convertToDTO(group);
    }
    
    public StudyGroupDTO createGroup(StudyGroupDTO groupDTO) {
        // Remove the check for existing group by creator email to allow multiple groups
        StudyGroup group = convertToEntity(groupDTO);
        
        // Add creator as first member
        GroupMember creator = new GroupMember(
            groupDTO.getCreatorName(),
            groupDTO.getCreatorEmail(),
            groupDTO.getCreatorPhone()
        );
        creator.setStudyGroup(group);
        group.getMembers().add(creator);
        
        StudyGroup savedGroup = studyGroupRepository.save(group);
        return convertToDTO(savedGroup);
    }
    
    public StudyGroupDTO joinGroup(Long groupId, GroupMemberDTO memberDTO) {
        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Study group not found with id: " + groupId));
        
        // Check if group is full
        if (group.getCurrentMembers() >= group.getMaxMembers()) {
            throw new RuntimeException("Group is already full");
        }
        
        // Check if member already joined this specific group
        if (groupMemberRepository.existsByStudyGroupAndEmail(group, memberDTO.getEmail())) {
            throw new RuntimeException("You have already joined this group");
        }
        
        // Add new member
        GroupMember member = new GroupMember(
            memberDTO.getName(),
            memberDTO.getEmail(),
            memberDTO.getPhone()
        );
        member.setStudyGroup(group);
        group.getMembers().add(member);
        group.setCurrentMembers(group.getCurrentMembers() + 1);
        
        StudyGroup updatedGroup = studyGroupRepository.save(group);
        return convertToDTO(updatedGroup);
    }
    
    public StudyGroupDTO leaveGroup(Long groupId, String userEmail) {
        StudyGroup group = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Study group not found with id: " + groupId));
        
        // Check if user is the creator
        if (group.getCreatorEmail().equals(userEmail)) {
            throw new RuntimeException("Creator cannot leave the group. You must delete the group instead.");
        }
        
        GroupMember member = groupMemberRepository.findByStudyGroupAndEmail(group, userEmail)
                .orElseThrow(() -> new RuntimeException("You are not a member of this group"));
        
        group.getMembers().remove(member);
        group.setCurrentMembers(group.getCurrentMembers() - 1);
        groupMemberRepository.delete(member);
        
        StudyGroup updatedGroup = studyGroupRepository.save(group);
        return convertToDTO(updatedGroup);
    }
    
    public void deleteGroup(Long id, String creatorEmail) {
        StudyGroup group = studyGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Study group not found with id: " + id));
        
        // Verify that the user is the creator
        if (!group.getCreatorEmail().equals(creatorEmail)) {
            throw new RuntimeException("Only the group creator can delete this group");
        }
        
        studyGroupRepository.delete(group);
    }
    
    public List<StudyGroupDTO> getGroupsByCreator(String creatorEmail) {
        return studyGroupRepository.findByCreatorEmail(creatorEmail)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<StudyGroupDTO> getFilteredGroups(Integer year, Integer semester, String specialization) {
        if (year != null && semester != null && specialization != null && !specialization.isEmpty()) {
            return studyGroupRepository.findByStudentInfo(year, semester, specialization)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else if (year != null && semester != null) {
            return studyGroupRepository.findByYearAndSemester(year, semester)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else if (specialization != null && !specialization.isEmpty()) {
            return studyGroupRepository.findBySpecialization(specialization)
                    .stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } else {
            return getAllGroups();
        }
    }
    
    private StudyGroupDTO convertToDTO(StudyGroup group) {
        List<GroupMemberDTO> memberDTOs = group.getMembers().stream()
                .map(member -> new GroupMemberDTO(
                    member.getId(),
                    member.getName(),
                    member.getEmail(),
                    member.getPhone(),
                    member.getJoinedAt()
                ))
                .collect(Collectors.toList());
        
        return new StudyGroupDTO(
            group.getId(),
            group.getCreatorName(),
            group.getCreatorEmail(),
            group.getCreatorPhone(),
            group.getYear(),
            group.getSemester(),
            group.getSpecialization(),
            group.getModule(),
            group.getTopic(),
            group.getContactNumber(),
            group.getMaxMembers(),
            group.getCurrentMembers(),
            group.getCreatedAt(),
            memberDTOs
        );
    }
    
    private StudyGroup convertToEntity(StudyGroupDTO dto) {
        return new StudyGroup(
            dto.getCreatorName(),
            dto.getCreatorEmail(),
            dto.getCreatorPhone(),
            dto.getYear(),
            dto.getSemester(),
            dto.getSpecialization(),
            dto.getModule(),
            dto.getTopic(),
            dto.getContactNumber(),
            dto.getMaxMembers()
        );
    }
}