package org.example.model;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public class Profesor extends User{
    private int birthYear;
    private String placeOfBirth;
    private LocalTime birthTime;
    private String email;
    private List<UUID> coursesIds;
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
