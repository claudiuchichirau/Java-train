package com.example.demo.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_type", nullable = false)
    private CourseType courseType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Profesor professor;

    public Course() {
    }

    public Course(String courseName, Profesor professor, CourseType courseType) {
        this.courseId = UUID.randomUUID();
        this.professor = professor;
        this.courseName = courseName;
        this.courseType = courseType;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public Profesor getProfessor() {
        return this.professor;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
