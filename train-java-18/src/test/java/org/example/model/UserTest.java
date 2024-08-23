package org.example.model;

import static org.junit.jupiter.api.Assertions.*;
import org.example.model.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void shouldCreateUserWithCorrectFirstNamesAndLastName() {
        User user = new User("john-doe", "smith");
        assertEquals(List.of("John", "Doe"), user.getFirstNames());
        assertEquals("Smith", user.getLastName());
    }

    @Test
    void shouldThrowExceptionWhenFirstNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new User(null, "smith"));
    }

    @Test
    void shouldThrowExceptionWhenFirstNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new User("", "smith"));
    }

    @Test
    void shouldThrowExceptionWhenLastNameIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new User("john-doe", null));
    }

    @Test
    void shouldThrowExceptionWhenLastNameIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new User("john-doe", ""));
    }

    @Test
    void shouldReturnReversedFirstNames() {
        User user = new User("john-doe", "smith");
        assertEquals(List.of("nhoJ", "eoD"), user.getReversedFirstNames());
    }
}
