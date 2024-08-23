package org.example.model;

import java.util.UUID;

public class PairInfo {
    private UUID studentId;
    private double grade;

    public PairInfo(UUID studentId, double grade) {
        this.studentId = studentId;
        this.grade = grade;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public double getGrade() {
        return grade;
    }
}
