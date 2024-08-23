package org.example.model;

import java.time.LocalDate;
import java.util.UUID;

public abstract class AbstractEntity {
    private UUID id;
    private LocalDate creationDate;
    private LocalDate disableDate;
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
