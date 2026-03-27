package com.studentdashboard.repository;

import com.studentdashboard.model.GroupMember;
import com.studentdashboard.model.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    
    List<GroupMember> findByStudyGroup(StudyGroup studyGroup);
    
    Optional<GroupMember> findByStudyGroupAndEmail(StudyGroup studyGroup, String email);
    
    boolean existsByStudyGroupAndEmail(StudyGroup studyGroup, String email);
    
    long countByStudyGroup(StudyGroup studyGroup);
}