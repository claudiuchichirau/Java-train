package org.example.model;

import java.util.UUID;

public class Course {
    private UUID courseId;
    private UUID professorId;
    private String courseName;
    private CourseType courseType;

    public Course(String courseName, UUID professorId, CourseType courseType) {
        this.courseId = UUID.randomUUID();
        this.professorId = professorId;
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

    public UUID getProfessorId() {
        return professorId;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
