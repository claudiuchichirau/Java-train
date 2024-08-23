package org.example.model;

import java.util.UUID;

public class Review {
    private UUID studentId;
    private UUID professorId;
    private String question;
    private int rating;

    public Review(UUID professorId, UUID studentId, String question, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating-ul trebuie să fie între 1 și 5.");
        }
        this.professorId = professorId;
        this.studentId = studentId;
        this.question = question;
        this.rating = rating;
    }

    public UUID getProfessorId() {
        return professorId;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public String getQuestion() {
        return question;
    }

    public int getRating() {
        return rating;
    }
}
