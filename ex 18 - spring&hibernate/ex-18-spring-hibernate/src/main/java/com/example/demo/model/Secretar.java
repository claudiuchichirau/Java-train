package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "secretares")
public class Secretar extends User{
    public Secretar() {
    }

    public Secretar(String lastName, String firstName) {
        super(lastName, firstName);
    }
}
