package com.studentdashboard.controller;

import com.studentdashboard.dto.GPACalculationRequest;
import com.studentdashboard.service.GPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/gpa")
public class GPAController {

    @Autowired
    private GPAService gpaService;

    @PostMapping("/calculate")
    public ResponseEntity<Map<String, Double>> calculateGPA(@RequestBody GPACalculationRequest request) {
        double gpa = gpaService.calculateGPA(request);
        Map<String, Double> response = new HashMap<>();
        response.put("gpa", gpa);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
