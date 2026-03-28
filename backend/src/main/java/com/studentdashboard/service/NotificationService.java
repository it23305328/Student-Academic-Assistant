package com.studentdashboard.service;

import com.studentdashboard.dto.UpcomingAlertDTO;
import com.studentdashboard.model.Assignment;
import com.studentdashboard.model.Notification;
import com.studentdashboard.model.Timetable;
import com.studentdashboard.repository.AssignmentRepository;
import com.studentdashboard.repository.NotificationRepository;
import com.studentdashboard.repository.TimetableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private TimetableRepository timetableRepository;

    public List<Notification> getNotificationsForStudent(Long studentId) {
        return notificationRepository.findByStudentIdOrderByCreatedAtDesc(studentId);
    }

    public List<UpcomingAlertDTO> getUpcomingAlerts(Long studentId) {
        List<UpcomingAlertDTO> alerts = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        // 1. Check Assignments (Deadlines)
        List<Assignment> assignments = assignmentRepository.findByStudentIdOrderByDeadlineAsc(studentId);
        for (Assignment assignment : assignments) {
            if ("Pending".equalsIgnoreCase(assignment.getStatus()) && assignment.getDeadline() != null) {
                LocalDate deadlineDate = assignment.getDeadline().toLocalDate();
                if (deadlineDate.equals(today) || deadlineDate.equals(tomorrow)) {
                    Duration duration = Duration.between(now, assignment.getDeadline());
                    boolean isUrgent = duration.toHours() <= 24 && !duration.isNegative();
                    
                    String timeLabel = deadlineDate.equals(today) ? "Today" : "Tomorrow";
                    String timeStr = assignment.getDeadline().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

                    alerts.add(new UpcomingAlertDTO(
                        "A-" + assignment.getId(),
                        "Assignment",
                        assignment.getTitle(),
                        timeLabel + " at " + timeStr,
                        isUrgent,
                        isUrgent ? "CRITICAL: Submission due within 24 hours!" : "Reminder: Submission due " + timeLabel.toLowerCase()
                    ));
                }
            }
        }

        // 2. Check Timetable (Lectures/Exams)
        List<Timetable> todayLectures = timetableRepository.findByStudentIdAndDateOrderByStartTimeAsc(studentId, today);
        List<Timetable> tomorrowLectures = timetableRepository.findByStudentIdAndDateOrderByStartTimeAsc(studentId, tomorrow);

        for (Timetable lecture : todayLectures) {
            LocalDateTime lectureStart = LocalDateTime.of(lecture.getDate(), lecture.getStartTime());
            Duration duration = Duration.between(now, lectureStart);
            boolean isUpcoming = !duration.isNegative();
            boolean isUrgent = duration.toHours() < 2 && isUpcoming; // Urgent if within 2 hours

            if (isUpcoming) {
                alerts.add(new UpcomingAlertDTO(
                    "T-" + lecture.getId(),
                    "Timetable",
                    lecture.getSubjectName(),
                    "Today, " + lecture.getStartTime().toString(),
                    isUrgent,
                    isUrgent ? "Starting soon in " + duration.toMinutes() + " mins!" : "Scheduled for today at " + lecture.getVenue()
                ));
            }
        }

        for (Timetable lecture : tomorrowLectures) {
            alerts.add(new UpcomingAlertDTO(
                "T-" + lecture.getId(),
                "Timetable",
                lecture.getSubjectName(),
                "Tomorrow, " + lecture.getStartTime().toString(),
                false,
                "Scheduled for tomorrow at " + lecture.getVenue()
            ));
        }

        return alerts;
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
