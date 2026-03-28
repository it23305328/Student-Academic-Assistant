package com.studentdashboard.controller;

import com.studentdashboard.model.Student;
import com.studentdashboard.service.StudentService;
import com.studentdashboard.service.SmsService;
import com.studentdashboard.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SmsService smsService;

    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.registerStudent(student);
            return new ResponseEntity<>(savedStudent, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error registering student: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Student student = studentService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (student != null) {
            // Generate a dummy token for this implementation
            String dummyToken = UUID.randomUUID().toString();
            LoginResponse response = new LoginResponse(
                    dummyToken,
                    student.getName(),
                    student.getRole(),
                    student.getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Phone number is required"));
        }

        String otp = String.format("%06d", new Random().nextInt(999999));
        try {
            smsService.sendOTP(phoneNumber, otp);
            otpStorage.put(phoneNumber, otp);
            return ResponseEntity.ok(Map.of("message", "OTP sent successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "Failed to send OTP", "error", e.getMessage()));
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String phoneNumber = request.get("phoneNumber");
        String enteredOtp = request.get("otp");

        if (phoneNumber == null || enteredOtp == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Phone number and OTP are required"));
        }

        String storedOtp = otpStorage.get(phoneNumber);
        if (storedOtp != null && storedOtp.equals(enteredOtp)) {
            otpStorage.remove(phoneNumber);
            return ResponseEntity.ok(Map.of("success", true, "message", "OTP verified successfully"));
        } else {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Invalid OTP"));
        }
    }

    @GetMapping("/all-students")
    public java.util.List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Creating an inner class for the login request body so we don't need a
    // separate DTO file right away
    static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
