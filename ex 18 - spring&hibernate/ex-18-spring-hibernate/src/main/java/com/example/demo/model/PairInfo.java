package com.example.demo.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "pair_info")
public class PairInfo {
    @Id
    @GeneratedValue
    @Column(name = "student_id", updatable = false, nullable = false, unique = true)
    private UUID studentId;

    @Column(name = "student_grade", nullable = false)
    private double grade;

    public PairInfo() {
    }

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
