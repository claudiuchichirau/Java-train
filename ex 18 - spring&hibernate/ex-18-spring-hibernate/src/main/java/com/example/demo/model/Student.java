package com.example.demo.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "students")
public class Student extends User{
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_courses", joinColumns = @JoinColumn(name = "student_id"))
    @Column(name = "student_courses")
    private List<UUID> coursesIds = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "student_year", nullable = false)
    private StudyYear studyYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "learning_type", nullable = false)
    private LearningType learningType;

    @Enumerated(EnumType.STRING)
    @Column(name = "study_level", nullable = false)
    private StudyLevel studyLevel;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "student_exams", joinColumns = @JoinColumn(name = "student_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "exam_type")
    @Column(name = "grade")
    private final Map<ExamType, Double> studentExams = new HashMap<>();

    public Student() {
    }

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public List<UUID> getCoursesIds() {
        return coursesIds;
    }

    public LearningType getLearningType() {
        return learningType;
    }

    public StudyLevel getStudyLevel() {
        return studyLevel;
    }

    public StudyYear getStudyYear() {
        return studyYear;
    }

    public void setLearningType(LearningType learningType) {
        this.learningType = learningType;
    }

    public void setStudyLevel(StudyLevel studyLevel) {
        this.studyLevel = studyLevel;
    }

    public void setStudyYear(StudyYear studyYear) {
        this.studyYear = studyYear;
    }

    public void addExam(ExamType examType, Double grade) {
        studentExams.put(examType, grade);
    }

    public Map<ExamType, Double> getExams() {
        return studentExams;
    }
}
