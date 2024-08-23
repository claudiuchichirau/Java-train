package org.example.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class CatalogTest {
    @Test
    void shouldAddGradeWhenUserIsProfessorAndCourseAccessIsVerified() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        User professor = new Profesor("professor1", "professor1");

        Secretar secretar = new Secretar("secretary1", "secretary1");

        Student student = new Student("claudiu", "chichirau");
        catalog.addStudent(student, secretar);

        Course course = new Course("course1", professor.getUserId(), CourseType.MANDATORY);
        catalog.addCourse(course, secretar);

        PairInfo pairInfo = new PairInfo(student.getUserId(), 10.0);
        catalog.addGrade(professor, course.getCourseId(), pairInfo);
        Assertions.assertEquals(pairInfo, catalog.getPairInfo(course.getCourseId()));
    }

    @Test
    void shouldThrowExceptionWhenAddingGradeAndUserIsNotProfessor() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        User student = new Student("student1", "student1");
        UUID courseId = UUID.randomUUID();
        PairInfo pairInfo = new PairInfo(UUID.randomUUID(), 10.0);
        assertThrows(SecurityException.class, () -> catalog.addGrade(student, courseId, pairInfo));
    }

    @Test
    void shouldReturnStudentWhenStudentIdExists() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        Student student = new Student("student1", "student1");
        catalog.addStudent(student, new Secretar("secretary1", "secretary1"));
        Assertions.assertEquals(student, catalog.getStudentById(student.getUserId()));
    }

    @Test
    void shouldReturnNullWhenStudentIdDoesNotExist() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        assertNull(catalog.getStudentById(UUID.randomUUID()));
    }

    @Test
    void shouldReturnCourseWhenCourseIdExists() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        UUID professorId = UUID.randomUUID();

        Course course = new Course("course1", professorId, CourseType.MANDATORY);

        catalog.addCourse(course, new Secretar("secretary1", "secretary1"));
        Course newCourse = catalog.getCourses().stream().findFirst().orElse(null);
        Assertions.assertEquals(newCourse, catalog.getCourseById(newCourse.getCourseId()));
    }

    @Test
    void shouldReturnNullWhenCourseIdDoesNotExist() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        assertNull(catalog.getCourseById(UUID.randomUUID()));
    }

    @Test
    void shouldReturnTrueWhenStudentExists() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        Student student = new Student("claudiu", "chichirau");
        catalog.addStudent(student, new Secretar("secretary1", "secretary1"));
        List<String> studentFirstNames = student.getFirstNames();
        String concatenatedFirstNames = String.join("-", studentFirstNames);
        assertTrue(catalog.existsStudent(concatenatedFirstNames, student.getLastName()));
    }

    @Test
    void shouldReturnFalseWhenStudentDoesNotExist() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        assertFalse(catalog.existsStudent("nonexistent", "nonexistent"));
    }

    @Test
    void shouldReturnTrueWhenCourseExists() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        String courseName = "course1";
        UUID professorId = UUID.randomUUID();

        Course course = new Course(courseName, professorId, CourseType.MANDATORY);

        catalog.addCourse(course, new Secretar("secretary1", "secretary1"));
        assertTrue(catalog.existsCourse(courseName));
    }

    @Test
    void shouldReturnFalseWhenCourseDoesNotExist() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        assertFalse(catalog.existsCourse("nonexistent"));
    }

    @Test
    void shouldAddStudentWhenUserIsSecretary() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        Student student = new Student("claudiu", "chichirau");
        catalog.addStudent(student, new Secretar("secretary1", "secretary1"));
        String concatenatedFirstNames = String.join("-", student.getFirstNames());
        assertTrue(catalog.existsStudent(concatenatedFirstNames, student.getLastName()));
    }

    @Test
    void shouldThrowExceptionWhenAddingStudentAndUserIsNotSecretary() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        Student student = new Student("claudiu", "chichirau");
        assertThrows(SecurityException.class, () -> catalog.addStudent(student, new Student("student2", "student2")));
    }

    @Test
    void shouldAddCourseWhenUserIsSecretary() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        String courseName = "course1";
        UUID professorId = UUID.randomUUID();

        Course course = new Course(courseName, professorId, CourseType.MANDATORY);

        catalog.addCourse(course, new Secretar("secretary1", "secretary1"));
        assertTrue(catalog.existsCourse(courseName));
    }

    @Test
    void shouldThrowExceptionWhenAddingCourseAndUserIsNotSecretary() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        Course course = new Course("course1", UUID.randomUUID(), CourseType.MANDATORY);
        assertThrows(SecurityException.class, () -> catalog.addCourse(course, new Student("student1", "student1")));
    }

    @Test
    void shouldCalculateAverageGradeForStudent() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        Student student = new Student("claudiu", "chichirau");

        Secretar secretar = new Secretar("secretary1", "secretary1");

        catalog.addStudent(student, secretar);

        Course course = new Course("course1", UUID.randomUUID(), CourseType.MANDATORY);
        catalog.addCourse(course, secretar);

        PairInfo pairInfo = new PairInfo(student.getUserId(), 10.0);
        catalog.addGrade(new Profesor("professor1", "professor1"), course.getCourseId(), pairInfo);
        Assertions.assertEquals(10.0, catalog.calculateAverageGrade(student, null));
    }

    @Test
    void shouldCalculateAverageGradeForYear() {
        Catalog catalog = Catalog.getInstance("secretarKey");

        Secretar secretar = new Secretar("secretary1", "secretary1");

        Student student1 = new Student("student1", "student1");
        Student student2 = new Student("student2", "student2");

        catalog.addStudent(student1, secretar);
        catalog.addStudent(student2, secretar);

        Course course1 = new Course("course1", UUID.randomUUID(), CourseType.MANDATORY);
        Course course2 = new Course("course2", UUID.randomUUID(), CourseType.MANDATORY);
        catalog.addCourse(course1, secretar);
        catalog.addCourse(course2, secretar);

        PairInfo pairInfo1 = new PairInfo(student1.getUserId(), 10.0);
        PairInfo pairInfo2 = new PairInfo(student2.getUserId(), 8.0);
        catalog.addGrade(new Profesor("professor1", "professor1"), course1.getCourseId(), pairInfo1);
        catalog.addGrade(new Profesor("professor1", "professor1"), course2.getCourseId(), pairInfo2);
        Assertions.assertEquals(9.0, catalog.calculateAverageGrade(new Profesor("professor1", "professor1"), StudyYear.FIRST_YEAR));
    }

    @Test
    void shouldThrowExceptionWhenCalculatingAverageGradeAndUserIsNotAuthorized() {
        Catalog catalog = Catalog.getInstance("secretarKey");
        assertThrows(SecurityException.class, () -> catalog.calculateAverageGrade(new User("user1", "user1"), StudyYear.FIRST_YEAR));
    }
}