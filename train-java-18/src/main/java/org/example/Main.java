package org.example;

import org.example.model.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduceti tipul de utilizator (Student/Profesor/Secretar): ");
        String userType = scanner.nextLine().trim().toLowerCase();

        if (userType.equals("student")) {
            handleStudent(scanner);
        } else if (userType.equals("profesor")) {
            handleProfesor(scanner);
        } else if (userType.equals("secretar")) {
            handleSecretar(scanner);
        } else {
            System.out.println("Tip de utilizator necunoscut.");
        }

        scanner.close();
    }

    private static void handleStudent(Scanner scanner) {
        System.out.println("Autentificare ca student");
        System.out.println("Introduceti prenumele: ");
        String firstName = scanner.nextLine();

        System.out.println("Introduceti numele de familie: ");
        String lastName = scanner.nextLine();

        try {
            Catalog catalog = Catalog.getInstance("secretarKey"); // Înlocuiește cu cheia reală
            if (catalog.existsStudent(firstName, lastName)) {
                System.out.println("Bine ai venit, " + firstName + " " + lastName + ". Aici sunt materiile tale.");
            } else {
                System.out.println("Username-ul nu exista in catalog.");
            }
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleProfesor(Scanner scanner) {
        System.out.println("Autentificare ca profesor");

        System.out.println("Introduceti numele de familie: ");
        String lastName = scanner.nextLine();

        System.out.println("Introduceti prenumele: ");
        String firstName = scanner.nextLine();

        Profesor profesor = new Profesor(lastName, firstName);

        System.out.println("Introduceti cheia de acces pentru catalog: ");
        String key = scanner.nextLine().trim();

        try {
            Catalog catalog = Catalog.getInstance(key);
            System.out.println("Acces permis. Bine ai venit, Profesor.");

            // Meniu pentru profesor
            while (true) {
                System.out.println("\nOptiuni:");
                System.out.println("1. Adauga nota");
                System.out.println("2. Vizualizeaza nota");
                System.out.println("3. Iesire");

                String option = scanner.nextLine().trim();

                if (option.equals("1")) {
                    addGrade(profesor, scanner, catalog);
                } else if (option.equals("2")) {
                    viewGrade(scanner, catalog);
                } else if (option.equals("3")) {
                    break;
                } else {
                    System.out.println("Optiune invalida.");
                }
            }

        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void handleSecretar(Scanner scanner) {
        System.out.println("Autentificare ca secretar...");

        System.out.println("Introduceti numele de familie: ");
        String lastName = scanner.nextLine();

        System.out.println("Introduceti prenumele: ");
        String firstName = scanner.nextLine();

        Secretar secretar = new Secretar(lastName, firstName);

        System.out.println("Introduceti cheia de acces pentru catalog: ");
        String key = scanner.nextLine().trim();

        try {
            Catalog catalog = Catalog.getInstance(key);
            System.out.println("Acces permis. Bine ai venit, Secretar.");

            while (true) {
                System.out.println("\nOptiuni:");
                System.out.println("1. Adauga student");
                System.out.println("2. Adauga curs");
                System.out.println("3. Logout");
                System.out.println("4. Iesire");

                String option = scanner.nextLine().trim();

                if (option.equals("1")) {
                    System.out.println("Introdu username-ul studentului pe care vrei sa-l adaugi in catalog:");
                    System.out.println("Prenumele studentului: ");
                    String studentFirstName = scanner.nextLine();

                    System.out.println("Numele de familie al studentului: ");
                    String studentLastName = scanner.nextLine();

                    Student student = new Student(studentFirstName, studentLastName);

                    catalog.addStudent(student, secretar);
                } else if (option.equals("2")) {
                    break;          // TO DO: Adauaga curs
                } else if (option.equals("3")) {
                    System.out.println("Delogare...");
                    main(new String[]{});
                    break;
                } else if (option.equals("4")) {
                    break;
                } else {
                    System.out.println("Optiune invalida.");
                }
            }

        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addGrade(Profesor profesor, Scanner scanner, Catalog catalog) {
        System.out.println("Introduceti ID-ul materiei: ");
        UUID courseId = UUID.fromString(scanner.nextLine().trim());

        System.out.println("Introduceti ID-ul studentului: ");
        UUID studentId = UUID.fromString(scanner.nextLine().trim());

        System.out.println("Introduceti nota: ");
        int grade = Integer.parseInt(scanner.nextLine().trim());

        PairInfo pairInfo = new PairInfo(studentId, grade);
        catalog.addGrade(profesor, courseId, pairInfo);
        System.out.println("Nota a fost adaugata cu succes.");
    }

    private static void viewGrade(Scanner scanner, Catalog catalog) {
        System.out.println("Introduceti ID-ul materiei: ");
        UUID courseId = UUID.fromString(scanner.nextLine().trim());

        PairInfo pairInfo = catalog.getPairInfo(courseId);
        if (pairInfo != null) {
            System.out.println("Nota pentru studentul cu ID " + pairInfo.getStudentId() + " este: " + pairInfo.getGrade());
        } else {
            System.out.println("Nu exista nicio nota pentru aceasta materie.");
        }
    }
}
