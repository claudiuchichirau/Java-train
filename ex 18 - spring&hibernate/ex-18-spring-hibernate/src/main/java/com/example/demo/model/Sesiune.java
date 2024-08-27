package com.example.demo.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class Sesiune {
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "student_exam_map", joinColumns = @JoinColumn(name = "sesiune_id"))
    @MapKeyColumn(name = "student_id")
    @Column(name = "exam_scores")
    private static final Map<UUID, Map<ExamType, Double>> studentExamMap = new HashMap<>();

    public static void addStudentExam(UUID studentId, ExamType examType, Double grade) {
        if (studentExamMap.containsKey(studentId)) {
            studentExamMap.get(studentId).put(examType, grade);
        } else {
            Map<ExamType, Double> examMap = new HashMap<>();
            examMap.put(examType, grade);
            studentExamMap.put(studentId, examMap);
        }
    }

    public static Map<ExamType, Double> getExamsForStudent(UUID studentId) {
        return studentExamMap.getOrDefault(studentId, new HashMap<>());
    }

    public static Map<UUID, Map<ExamType, Double>> getAllExams() {
        return studentExamMap;
    }
}
