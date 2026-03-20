package com.studentdashboard.service;

import com.studentdashboard.model.Student;
import com.studentdashboard.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student registerStudent(Student student) {
        if (!student.getEmail().toLowerCase().endsWith("@my.sliit.lk")) {
            throw new RuntimeException("Only university email addresses (@my.sliit.lk) are allowed");
        }
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        if (student.getRole() == null || student.getRole().isEmpty()) {
            student.setRole("STUDENT");
        }
        return studentRepository.save(student);
    }

    public Student login(String email, String password) {
        Optional<Student> studentOpt = studentRepository.findByEmail(email);

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            // In a real application, you should compare hashed passwords using a library
            // like BCrypt
            if (student.getPassword().equals(password)) {
                if (student.getRole() == null || student.getRole().isEmpty()) {
                    student.setRole("STUDENT");
                }
                return student;
            }
        }
        return null; // or throw a custom exception
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }
}
