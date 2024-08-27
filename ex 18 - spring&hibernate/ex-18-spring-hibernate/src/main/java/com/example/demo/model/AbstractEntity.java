package com.example.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@MappedSuperclass
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "creation_date", updatable = false, nullable = false)
    private LocalDate creationDate;

    @Column(name = "disable_date")
    private LocalDate disableDate;

    @Column(name = "update_date")
    private LocalDate updateDate;

    public AbstractEntity() {
        id = UUID.randomUUID();
        creationDate = LocalDate.now();
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getDisableDate() {
        return disableDate;
    }

    public void setDisableDate(LocalDate disableDate) {
        this.disableDate = disableDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }
}
