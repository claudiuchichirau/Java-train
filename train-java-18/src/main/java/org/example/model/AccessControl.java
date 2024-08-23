package org.example.model;

import java.util.function.Predicate;

public class AccessControl {

    public static Predicate<User> isSecretar = new Predicate<User>() {
        @Override
        public boolean test(User user) {
            return user instanceof Secretar;
        }
    };

    public static Predicate<User> isProfesor = new Predicate<User>() {
        @Override
        public boolean test(User user) {
            return user instanceof Profesor;
        }
    };

    public static Predicate<User> isStudent = new Predicate<User>() {
        @Override
        public boolean test(User user) {
            return user instanceof Student;
        }
    };

    public static Predicate<User> canAddStudent = isSecretar;
}
