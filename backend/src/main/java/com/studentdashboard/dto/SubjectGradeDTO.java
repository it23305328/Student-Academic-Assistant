package com.studentdashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectGradeDTO {
    private String subjectName;
    private int credits;
    private String grade;
}
