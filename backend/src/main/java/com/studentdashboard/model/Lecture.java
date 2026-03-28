package com.studentdashboard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lectures")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String academicYear;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private String course;

    @Column(nullable = false)
    private String moduleName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String youtubeUrl;

    @Column(nullable = false, length = 1000)
    private String description;
}
