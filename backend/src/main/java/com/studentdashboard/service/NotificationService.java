package com.studentdashboard.service;

import com.studentdashboard.model.Assignment;
import com.studentdashboard.model.Notification;
import com.studentdashboard.repository.AssignmentRepository;
import com.studentdashboard.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> getNotificationsForStudent(Long studentId) {
        return notificationRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
    }

    // Run every hour: fixedRate = 3600000 ms = 1 hour
    @Scheduled(fixedRate = 3600000)
    public void checkAssignmentDeadlines() {
        System.out.println("Running assignment deadline check task...");
        List<Assignment> pendingAssignments = assignmentRepository.findAll().stream()
                .filter(a -> "Pending".equalsIgnoreCase(a.getStatus()))
                .toList();

        LocalDateTime now = LocalDateTime.now();

        for (Assignment assignment : pendingAssignments) {
            if (assignment.getDeadline() == null)
                continue;

            Duration duration = Duration.between(now, assignment.getDeadline());
            long hoursLeft = duration.toHours();

            // Very simple threshold matching for demo:
            // 72 hours (3 days), 24 hours (1 day), 1 hour
            // Note: Since this runs every hour, we just check if it matches the current
            // hour threshold.
            if (hoursLeft > 0) {
                if (hoursLeft == 72) {
                    createAlert(assignment, "3 days");
                } else if (hoursLeft == 24) {
                    createAlert(assignment, "1 day");
                } else if (hoursLeft == 1) {
                    createAlert(assignment, "1 hour");
                }
            }
        }
    }

    private void createAlert(Assignment assignment, String timeleftText) {
        Notification notification = new Notification();
        notification.setStudentId(assignment.getStudentId());
        notification.setMessage(
                "Reminder: Your assignment '" + assignment.getTitle() + "' is due in " + timeleftText + "!");
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        notificationRepository.save(notification);
    }
}
