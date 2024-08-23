package org.example.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Sesiune {
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
