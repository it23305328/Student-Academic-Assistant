package com.studentdashboard.controller;

import com.studentdashboard.model.Notification;
import com.studentdashboard.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable("studentId") Long studentId) {
        List<Notification> notifications = notificationService.getNotificationsForStudent(studentId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}
