package com.studentdashboard.service;

import com.studentdashboard.dto.GPACalculationRequest;
import com.studentdashboard.dto.SubjectGradeDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GPAService {

    private static final Map<String, Double> GRADE_SCALE = new HashMap<>();

    static {
        GRADE_SCALE.put("A+", 4.0);
        GRADE_SCALE.put("A", 4.0);
        GRADE_SCALE.put("A-", 3.7);
        GRADE_SCALE.put("B+", 3.3);
        GRADE_SCALE.put("B", 3.0);
        GRADE_SCALE.put("B-", 2.7);
        GRADE_SCALE.put("C+", 2.3);
        GRADE_SCALE.put("C", 2.0);
        GRADE_SCALE.put("C-", 1.7);
        GRADE_SCALE.put("D", 1.0);
        GRADE_SCALE.put("F", 0.0);
    }

    public double calculateGPA(GPACalculationRequest request) {
        if (request == null || request.getSubjects() == null || request.getSubjects().isEmpty()) {
            return 0.0;
        }

        double totalGradePoints = 0;
        int totalCredits = 0;

        for (SubjectGradeDTO subject : request.getSubjects()) {
            if (subject == null)
                continue;

            String grade = subject.getGrade() != null ? subject.getGrade().toUpperCase() : "F";
            double gradeValue = GRADE_SCALE.getOrDefault(grade, 0.0);

            totalGradePoints += (gradeValue * subject.getCredits());
            totalCredits += subject.getCredits();
        }

        if (totalCredits == 0)
            return 0.0;

        double result = totalGradePoints / totalCredits;
        return Math.round(result * 100.0) / 100.0;
    }
}
