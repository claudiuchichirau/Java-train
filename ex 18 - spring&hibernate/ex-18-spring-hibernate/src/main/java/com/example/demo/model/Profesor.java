package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "profesori")
public class Profesor extends User{
    @Column(name = "birth_year", nullable = false)
    private int birthYear;

    @Column(name = "birth_place")
    private String placeOfBirth;

    @Column(name = "birth_time")
    private LocalTime birthTime;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "profesor_courses", joinColumns = @JoinColumn(name = "profesor_id"))
    @Column(name = "courses_id")
    private List<UUID> coursesIds;

    public Profesor() {
    }

    public Profesor(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public LocalTime getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(LocalTime birthTime) {
        this.birthTime = birthTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
