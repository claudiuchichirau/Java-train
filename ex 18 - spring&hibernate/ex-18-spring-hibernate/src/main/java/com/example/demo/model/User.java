package com.example.demo.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@MappedSuperclass
public class User extends AbstractEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_id", updatable = false, nullable = false, unique = true)
    private UUID userId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_first_names", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "first_name")
    private List<String> firstNames = new ArrayList<>();

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public User() {
    }

    public User(String firstName, String lastName) {
        super();
        this.userId = UUID.randomUUID();
        this.firstNames = processFirstNames(firstName);
        this.lastName = capitalizeFirstLetter(lastName);
    }

    private List<String> processFirstNames(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        List<String> firstNamesList = new ArrayList<>();
        String[] names = firstName.split("-");
        for (String name : names) {
            firstNamesList.add(capitalizeFirstLetter(name.trim()));
        }
        return firstNamesList;
    }

    private String capitalizeFirstLetter(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public UUID getUserId(){
        return this.userId;
    }

    public List<String> getFirstNames(){
        return this.firstNames;
    }

    public List<String> getReversedFirstNames() {
        List<String> reversedNames = new ArrayList<>();
        for (String name : firstNames) {
            StringBuilder sb = new StringBuilder(name);
            reversedNames.add(sb.reverse().toString());
        }
        return reversedNames;
    }

    public String getLastName(){
        return this.lastName;
    }
}
