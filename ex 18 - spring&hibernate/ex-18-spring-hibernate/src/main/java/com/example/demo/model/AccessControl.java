package com.example.demo.model;

import java.util.function.Predicate;

public class AccessControl {

    public static final Predicate<User> isSecretar = user -> user instanceof Secretar;
    public static final Predicate<User> isProfesor = user -> user instanceof Profesor;
    public static final Predicate<User> isStudent = user -> user instanceof Student;

    public static final Predicate<User> canAddStudent = isSecretar;

}
