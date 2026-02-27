package com.studentdashboard.service;

import com.studentdashboard.model.Student;
import com.studentdashboard.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student registerStudent(Student student) {
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
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
                return student;
            }
        }
        return null; // or throw a custom exception
    }
}
