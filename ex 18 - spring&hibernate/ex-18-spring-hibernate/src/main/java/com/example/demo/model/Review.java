package com.example.demo.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue
    @Column(name = "review_id", updatable = false, nullable = false, unique = true)
    private UUID reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id", nullable = false)
    private Profesor professor;

    @Column(name = "question", nullable = false)
    private String question;

    @Column(name = "rating", nullable = false)
    private int rating;

    public Review() {
    }

    public Review(Student student, Profesor profesor, String question, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating-ul trebuie să fie între 1 și 5.");
        }
        this.student = student;
        this.professor = profesor;
        this.question = question;
        this.rating = rating;
    }

    public Profesor getProfessor() {
        return professor;
    }

    public Student  getStudent() {
        return student;
    }

    public String getQuestion() {
        return question;
    }

    public int getRating() {
        return rating;
    }
}
