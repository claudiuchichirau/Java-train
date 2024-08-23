package org.example.model;

import java.util.Collections;
import java.util.List;

public class Menu {
    private static final List<String> professorKeys = List.of("profKey1", "profKey2", "profKey3", "secretarKey");

    public static List<String> getProfessorKeys() {
        return Collections.unmodifiableList(professorKeys);
    }
}