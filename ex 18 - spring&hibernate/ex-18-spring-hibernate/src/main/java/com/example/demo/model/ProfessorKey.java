package com.example.demo.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "professors_keys")
public class ProfessorKey {
    @Id
    @GeneratedValue
    @Column(name = "professor_key_id", updatable = false, nullable = false, unique = true)
    private UUID professorKeyId;

    @Column(name = "key", nullable = false)
    private String keyValue;

    public ProfessorKey() {
    }

    public ProfessorKey(String keyValue) {
        this.keyValue = keyValue;
    }

    public UUID getId() {
        return professorKeyId;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
}
