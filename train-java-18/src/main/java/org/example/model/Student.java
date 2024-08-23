package org.example.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Student extends User{
    private List<UUID> coursesIds;
    private StudyYear studyYear;
    private LearningType learningType;
    private StudyLevel studyLevel;
    private final Map<ExamType, Double> studentExams = new HashMap<>();

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
